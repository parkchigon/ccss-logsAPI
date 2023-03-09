package com.lgu.common.tlo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

@Component
public class TUnionLog {

	/*--------------------------------------------------------------------------*/
	/* 전역 변수 선언 (환경변수) */
	/*--------------------------------------------------------------------------*/
	private String lsTloThisLogInd = ""; /* Log표시 */
	private String lsTloErrLogPath = ""; /* Err Log표시 */
	private String lsTloWriteInd = ""; /* TLO Log표시 */
	private String lsTloLogPath = ""; /* Log Home */
	private String lsTloLogOnMPath = ""; /* Log Home */
	private String lsTloServiceName = ""; /* Service Code */
	private String lsTloServerCode = ""; /* Server Code */
	private String lsTloServerID = ""; /* Server Code */
	private String lsTloWriteTerm = ""; /* 파일생성주기 */
	private String lsTloWriteTermOnm = ""; /* O&M */
	/*--------------------------------------------------------------------------*/
	/* 전역 변수 선언 (simulation 환경변수) */
	/*--------------------------------------------------------------------------*/
	private String lsTloWriteTermH = ""; /* 몇초마다write */
	private String lsTloDataCnt = ""; /* 몇건씩write */
	private String lsTloReadCnt = ""; /* 몇번 읽을지 */
	private String lsTloSrcLogFile = ""; /* SrcLog파일명 */
	private String lsTloTimeSystem = ""; /* Sys 시간사용 */
	/*--------------------------------------------------------------------------*/
	/* 전역 변수 선언 */
	/*--------------------------------------------------------------------------*/
	private final String lsSep = "|"; /* 로그내 구분자 */
	private String lsLogInd = ""; /* LOG_IND */
	private String lsLogFileName = ""; /* Log FileName */
	private String lsLogDirName = ""; /* Log Directory */
	private String lsSeparator = ""; /* Separator */
	//private StringBuffer sb = new StringBuffer(); /* StringBuffer */
	private String lsTestDir = ""; /* Test시 Dir */
	private Random rand = new Random(); /* Random */

	private String seqNumber = "0000";

	public TUnionLog() {

		try {
			/*------------------------------------------------------------------*/
			/* 운영체제(UNIX, DOS)에 따른 Separator \, / */
			/*------------------------------------------------------------------*/
			lsSeparator = File.separator;
			
			/*--------------------------------------------------------------------------*/
			/* default_path 가져오기 */
			/*--------------------------------------------------------------------------*/			
			String config_path = this.getClass().getClassLoader().getResource("").getPath();
			config_path += "/" + "config";

			String location = System.getProperty("spring.config.location");
			if (location != null && location.length() > 1) {
				config_path = location;
			}
			
			String active = System.getProperty("spring.profiles.active");
			
			//config_path += lsSeparator + active + lsSeparator + "TLOConfig.ini";
			config_path += "/" + active + "/" + "TLOConfig.ini";
			
			System.out.println("TLOConfig.ini파일=" + config_path);
			
			prtLog("Initialize");
			prtLog("TLOConfig.ini파일=" + config_path);
			/*------------------------------------------------------------------*/
			/* 환경변수 존재 여부 체크 */
			/*------------------------------------------------------------------*/
			String lsIniLine = ""; /* 라인별내용 */
			// int liSub = -1; /* 0부터시작 */

			File loIniFile = new File(config_path);
			if (!loIniFile.exists()) /* 존재여부 */
			{
				System.out.println(config_path + " 파일이 존재하지 않습니다.");
				return;
			}
			/*------------------------------------------------------------------*/
			/* TLOConfig.ini 파일 읽어 배열에 저장 */
			/*------------------------------------------------------------------*/
			RandomAccessFile loIniAccess = new RandomAccessFile(loIniFile, "r");
			while ((lsIniLine = loIniAccess.readLine()) != null) {
				lsIniLine = new String(lsIniLine.getBytes("8859_1"));
				if (lsIniLine.length() > 0 && !"#".equals(lsIniLine.substring(0, 1))) /* 주석제거 */ {
					/*----------------------------------------------------------*/
					/* 문장중 주석 제거 */
					/*----------------------------------------------------------*/
					int liSharp = lsIniLine.indexOf("#");
					if (liSharp > 0)
						lsIniLine = lsIniLine.substring(0, liSharp);
					/*----------------------------------------------------------*/
					/* 주석이 제거된 문장 이 Name, Value의 형태가 아닐 경우 다음 라인을 진행 */
					/*----------------------------------------------------------*/
					if (lsIniLine.indexOf("=") < 0)
						continue;
					/*----------------------------------------------------------*/
					/* Name, Value 별로 변수에 저장 */
					/*----------------------------------------------------------*/
					StringTokenizer st = new StringTokenizer(lsIniLine, "=");
					String lsNameV = st.nextToken().trim();
					String lsValueV = st.nextToken().trim();

					if ("TLO_THIS_LOG_IND".equals(lsNameV)) {
						lsTloThisLogInd = lsValueV;
					} else if ("TLO_WRITE_IND".equals(lsNameV)) {
						lsTloWriteInd = lsValueV;
					} else if ("TLO_LOG_PATH".equals(lsNameV)) {
						lsTloLogPath = asc2ksc(lsValueV);
						prtLog("lsTloLogPath=" + lsTloLogPath);
					} else if ("TLO_SERVICE_NAME".equals(lsNameV)) {
						lsTloServiceName = lsValueV;
					} else if ("TLO_SERVER_CODE".equals(lsNameV)) {
						lsTloServerCode = lsValueV;
					} else if ("TLO_SERVER_ID".equals(lsNameV)) {
						lsTloServerID = lsValueV;
					} else if ("TLO_WRITE_TERM".equals(lsNameV)) {
						lsTloWriteTerm = lsValueV;
					}
					/*----------------------------------------------------------*/
					/* Simulation에서 사용 */
					/*----------------------------------------------------------*/
					else if ("TLO_WRITE_TERM_H".equals(lsNameV)) {
						lsTloWriteTermH = lsValueV;
					} else if ("TLO_DATA_CNT".equals(lsNameV)) {
						lsTloDataCnt = lsValueV;
					} else if ("TLO_READ_CNT".equals(lsNameV)) {
						lsTloReadCnt = lsValueV;
					} else if ("TLO_SRC_LOG_FILE".equals(lsNameV)) {
						lsTloSrcLogFile = asc2ksc(lsValueV);
					} else if ("TLO_TIME_SYSTEM".equals(lsNameV)) {
						lsTloTimeSystem = lsValueV;
					}
				}
			}
			/*------------------------------------------------------------------*/
			/* 환경파일 close */
			/*------------------------------------------------------------------*/
			loIniAccess.close();
			
			String wasId = System.getProperty("WAS_ID");
			if (wasId != null && wasId.length() > 0) {
				lsTloServerCode = wasId;
				lsTloServerID = wasId;
			}
			
			/*------------------------------------------------------------------*/
			/* 환경변수가 환경파일에 없거나 값이 없을 경우 */
			/*------------------------------------------------------------------*/
			if ("".equals(lsTloThisLogInd)) {
				lsTloThisLogInd = "false";
				prtErr("환경변수 TLO_THIS_LOG_IND가 존재 하지 않아 false로 설정 합니다.");
			}
			if ("".equals(lsTloWriteInd)) {
				lsTloWriteInd = "false";
				prtErr("환경변수 TLO_WRITE_IND가 존재 하지 않아 false로 설정 합니다.");
			}
			if ("".equals(lsTloLogPath)) {
				prtErr("환경변수 TLO_LOG_PATH가 존재 하지 않습니다.");
			}
			if ("".equals(lsTloServiceName)) {
				prtErr("환경변수 TLO_SERVICE_NAME가 존재 하지 않습니다.");
			}
			if ("".equals(lsTloServerCode)) {
				prtErr("환경변수 TLO_SERVER_CODE가 존재 하지 않습니다.");
			}
			if ("".equals(lsTloServerID)) {
				prtErr("환경변수 TLO_SERVER_ID가 존재 하지 않습니다.");
			}
			if ("".equals(lsTloWriteTerm)) {
				lsTloWriteTerm = "60";
				prtErr("환경변수 TLO_WRITE_TERM가 존재 하지 않아 60으로 설정 합니다.");
			}
		} catch (Exception err) {
			prtErr("초기화중오류발생:" + err);
		}
	}

	// TLOConfig.ini 파일을 파일 경로 파라미터로 가지고 오는 함수...
	public TUnionLog(String config_path) {
		try {
			prtLog("Initialize");
			/*------------------------------------------------------------------*/
			/* 운영체제(UNIX, DOS)에 따른 Separator \, / */
			/*------------------------------------------------------------------*/
			lsSeparator = File.separator;
			prtLog("TLOConfig.ini파일=" + config_path);
			/*------------------------------------------------------------------*/
			/* 환경변수 존재 여부 체크 */
			/*------------------------------------------------------------------*/
			String lsIniLine = ""; /* 라인별내용 */
			// int liSub = -1; /* 0부터시작 */

			File loIniFile = new File(config_path);
			if (!loIniFile.exists()) /* 존재여부 */
			{
				// System.out.println(config_path + " 파일이 존재하지 않습니다.");
				return;
			}
			/*------------------------------------------------------------------*/
			/* TLOConfig.ini 파일 읽어 배열에 저장 */
			/*------------------------------------------------------------------*/
			RandomAccessFile loIniAccess = new RandomAccessFile(loIniFile, "r");
			while ((lsIniLine = loIniAccess.readLine()) != null) {
				lsIniLine = new String(lsIniLine.getBytes("8859_1"));
				if (lsIniLine.length() > 0 && !"#".equals(lsIniLine.substring(0, 1))) /* 주석제거 */ {
					/*----------------------------------------------------------*/
					/* 문장중 주석 제거 */
					/*----------------------------------------------------------*/
					int liSharp = lsIniLine.indexOf("#");
					if (liSharp > 0)
						lsIniLine = lsIniLine.substring(0, liSharp);
					/*----------------------------------------------------------*/
					/* 주석이 제거된 문장 이 Name, Value의 형태가 아닐 경우 다음 라인을 진행 */
					/*----------------------------------------------------------*/
					if (lsIniLine.indexOf("=") < 0)
						continue;
					/*----------------------------------------------------------*/
					/* Name, Value 별로 변수에 저장 */
					/*----------------------------------------------------------*/
					StringTokenizer st = new StringTokenizer(lsIniLine, "=");
					String lsNameV = st.nextToken().trim();
					String lsValueV = st.nextToken().trim();

					if ("TLO_THIS_LOG_IND".equals(lsNameV)) {
						lsTloThisLogInd = lsValueV;
					} else if ("TLO_WRITE_IND".equals(lsNameV)) {
						lsTloWriteInd = lsValueV;
					} else if ("TLO_LOG_PATH".equals(lsNameV)) {
						lsTloLogPath = asc2ksc(lsValueV);
						prtLog("lsTloLogPath=" + lsTloLogPath);
					} else if ("TLO_SERVICE_NAME".equals(lsNameV)) {
						lsTloServiceName = lsValueV;
					} else if ("TLO_SERVER_CODE".equals(lsNameV)) {
						lsTloServerCode = lsValueV;
					} else if ("TLO_SERVER_ID".equals(lsNameV)) {
						lsTloServerID = lsValueV;
					} else if ("TLO_WRITE_TERM".equals(lsNameV)) {
						lsTloWriteTerm = lsValueV;
					}
					/*----------------------------------------------------------*/
					/* Simulation에서 사용 */
					/*----------------------------------------------------------*/
					else if ("TLO_WRITE_TERM_H".equals(lsNameV)) {
						lsTloWriteTermH = lsValueV;
					} else if ("TLO_DATA_CNT".equals(lsNameV)) {
						lsTloDataCnt = lsValueV;
					} else if ("TLO_READ_CNT".equals(lsNameV)) {
						lsTloReadCnt = lsValueV;
					} else if ("TLO_SRC_LOG_FILE".equals(lsNameV)) {
						lsTloSrcLogFile = asc2ksc(lsValueV);
					} else if ("TLO_TIME_SYSTEM".equals(lsNameV)) {
						lsTloTimeSystem = lsValueV;
					}
				}
			}
			/*------------------------------------------------------------------*/
			/* 환경파일 close */
			/*------------------------------------------------------------------*/
			loIniAccess.close();
			
			String wasId = System.getProperty("WAS_ID");
			if (wasId != null && wasId.length() > 0) {
				lsTloServerCode = wasId;
				lsTloServerID = wasId;
			}
			
			/*------------------------------------------------------------------*/
			/* 환경변수가 환경파일에 없거나 값이 없을 경우 */
			/*------------------------------------------------------------------*/
			if ("".equals(lsTloThisLogInd)) {
				lsTloThisLogInd = "false";
				prtErr("환경변수 TLO_THIS_LOG_IND가 존재 하지 않아 false로 설정 합니다.");
			}
			if ("".equals(lsTloWriteInd)) {
				lsTloWriteInd = "false";
				prtErr("환경변수 TLO_WRITE_IND가 존재 하지 않아 false로 설정 합니다.");
			}
			if ("".equals(lsTloLogPath)) {
				prtErr("환경변수 TLO_LOG_PATH가 존재 하지 않습니다.");
			}
			if ("".equals(lsTloServiceName)) {
				prtErr("환경변수 TLO_SERVICE_NAME가 존재 하지 않습니다.");
			}
			if ("".equals(lsTloServerCode)) {
				prtErr("환경변수 TLO_SERVER_CODE가 존재 하지 않습니다.");
			}
			if ("".equals(lsTloServerID)) {
				prtErr("환경변수 TLO_SERVER_ID가 존재 하지 않습니다.");
			}
			if ("".equals(lsTloWriteTerm)) {
				lsTloWriteTerm = "60";
				prtErr("환경변수 TLO_WRITE_TERM가 존재 하지 않아 60으로 설정 합니다.");
			}
		} catch (Exception err) {
			prtErr("초기화중오류발생:" + err);
		}
	}

	/*--------------------------------------------------------------------------*/
	/* 공통StartTran. Directory, File명 결정. 기본 log문장 저장 */
	/*--------------------------------------------------------------------------*/
	public void startTran(String asTraceId, StringBuffer sb) {

		/*----------------------------------------------------------------------*/
		/* Test폴더 선택 시 LogPath를 Test폴더로 변경 */
		/*----------------------------------------------------------------------*/
		if (lsTestDir.length() > 0) {
			lsTloLogPath = lsTestDir;
			lsTloLogOnMPath = lsTestDir; // + lsSeparator + "OnM"
		}
		/*----------------------------------------------------------------------*/
		/* TLO_LOG_PATH 폴더 */
		/*----------------------------------------------------------------------*/
		File lsTloLogPathDir = new File(lsTloLogPath);

		prtLog("TLO_LOG_PATH=" + lsTloLogPathDir);
		if (!(lsTloLogPathDir.exists() && lsTloLogPathDir.isDirectory())) {
			lsTloLogPathDir.mkdir();
			prtLog("TLO_LOG_PATH 디렉토리가 존재하지 않아 생성");
		}
		/*----------------------------------------------------------------------*/
		/* Log형태를 Log문자열에 저장 */
		/*----------------------------------------------------------------------*/

		sb.delete(0, sb.length()); /* sb 초기화 */
		sb.append("SEQ_ID=").append(asTraceId).append(lsSep);
		sb.append("LOG_TIME=");

	}

	/*--------------------------------------------------------------------------*/
	/* 로그를 Name, Value 형태로 생성한다 */
	/*--------------------------------------------------------------------------*/
	public void setElement(String elmtName, String elmtValue, StringBuffer sb) {
		// System.out.println("elmtName>>>>>"+elmtName);
		// System.out.println("elmtValue>>>>>"+elmtValue);
		/*----------------------------------------------------------------------*/
		/* ELEMENT name, value에 해당하는 문자열 저장 */
		/*----------------------------------------------------------------------*/
		sb.append(lsSep);
		if (elmtName.length() > 0 || elmtValue.length() > 0)
			sb.append(elmtName).append("=").append(elmtValue);

		prtLog("setElement->" + elmtName + "=" + elmtValue);
	}

	/*--------------------------------------------------------------------------*/
	/* 공통EndTran. File 생성 */
	/*--------------------------------------------------------------------------*/
	public int endTran(String asLogTime, StringBuffer sb) {

		prtLog("endTran");
		/*----------------------------------------------------------------------*/
		/* Log 파일 Make */
		/*----------------------------------------------------------------------*/
		try {
			String lsFilePath = "";
			/*------------------------------------------------------------------*/
			/* Log를 남기려고 할 경우만 실행 */
			/*------------------------------------------------------------------*/
			if ("true".equals(lsTloWriteInd.toLowerCase())) {
				/*--------------------------------------------------------------*/
				/* LOG_TIME값 변경. 순서상 앞에 존재하므로 */
				/*--------------------------------------------------------------*/
				int liValueIdx = sb.toString().indexOf("LOG_TIME=");
				liValueIdx += "LOG_TIME=".length();
				sb.replace(liValueIdx, liValueIdx, asLogTime);
				/*--------------------------------------------------------------*/
				/* Term */
				/*--------------------------------------------------------------*/
				String lsLogTime = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
				/*--------------------------------------------------------------*/
				/* O&M 로그의 생성주기를 별도 */
				/*--------------------------------------------------------------*/
				int liTerm = Integer
						.parseInt(("4".equals(lsLogInd) || "5".equals(lsLogInd)) ? lsTloWriteTermOnm : lsTloWriteTerm);
				int liMin = 0;
				if (liTerm > 0) {
					liMin = Integer.parseInt(lsLogTime.substring(8, 10)) * 60
							+ Integer.parseInt(lsLogTime.substring(10, 12));
					liMin = ((liMin / liTerm) * liTerm) % 60;
				}
				/*--------------------------------------------------------------*/
				/* 현재 날짜를 이용해 Directory 지정(20040701) */
				/*--------------------------------------------------------------*/
				lsLogDirName = lsLogTime.substring(0, 8);
				/*--------------------------------------------------------------*/
				/* 현재 날짜로 FileName 지정 */
				/*--------------------------------------------------------------*/
				lsLogFileName = lsTloServiceName + "." + lsTloServerCode + "." + lsLogDirName;
				/*--------------------------------------------------------------*/
				/* 1시간 이상일 경우는 시간까지의 이름으로 log생성 */
				/*--------------------------------------------------------------*/
				if (liTerm == 0) /* 하루단위 */
				{
					lsLogFileName += "0000.log";
				} else if (liMin >= 10) {
					lsLogFileName += lsLogTime.substring(8, 10) + liMin + ".log";
				} else if (liMin == 0) {
					lsLogFileName += lsLogTime.substring(8, 10) + "00.log";
				} else {
					lsLogFileName += lsLogTime.substring(8, 11) + liMin + ".log";
				}
				prtLog("FileName=" + lsLogFileName);
				/*--------------------------------------------------------------*/
				/* O&M 로그를 별도로 생성 해야 할 경우 */
				/*--------------------------------------------------------------*/
				if ("4".equals(lsLogInd) || "5".equals(lsLogInd)) {
					lsFilePath = lsTloLogOnMPath + lsSeparator + lsLogDirName;
				} else {
					lsFilePath = lsTloLogPath + lsSeparator + lsLogDirName;
				}
				/*--------------------------------------------------------------*/
				/* 오늘 날짜 폴더 */
				/*--------------------------------------------------------------*/
				File lsDateDir = new File(lsFilePath);

				if (!(lsDateDir.exists() && lsDateDir.isDirectory())) {
					lsDateDir.mkdir();
					prtLog("오늘날짜 디렉토리가 존재 하지 않아 생성 합니다");
				}
				/*--------------------------------------------------------------*/
				/* 현재 파일의 뒷시간에 파일이 존재하면 뒷시간 파일에 write */
				/*--------------------------------------------------------------*/
				String lsNow = lsLogFileName.substring(lsLogFileName.length() - 16, lsLogFileName.length() - 16 + 12);
				String lsAfter = "";
				String lsAfterFile = "";
				if (liTerm == 0) {
					lsAfter = calcDateTime(lsNow, "DAY", 1);
				} else {
					lsAfter = calcDateTime(lsNow, "MINUTE", liTerm);
				}
				/*--------------------------------------------------------------*/
				/* After시간이 현재시간과의 차이가 10초 내일때만 체크. */
				/*--------------------------------------------------------------*/
				File loFile;
				if (Long.parseLong(lsAfter) <= Long.parseLong(calcDateTime(lsLogTime, "SECOND", 10))) {
					lsAfterFile = lsFilePath.substring(0, lsFilePath.length() - 8) + lsAfter.substring(0, 8)
							+ lsSeparator + lsLogFileName.substring(0, lsLogFileName.length() - 16)
							+ lsAfter.substring(0, 12) + ".log";
					loFile = new File(lsAfterFile);
					/*----------------------------------------------------------*/
					/* File 객체 작성. 현재파일이 젤 마지막에 생성됐을 경우 */
					/*----------------------------------------------------------*/
					if (!loFile.exists()) {
						loFile = new File(lsFilePath + lsSeparator + lsLogFileName);
					}
					prtLog("After 체크함");
				} else {
					loFile = new File(lsFilePath + lsSeparator + lsLogFileName);
					prtLog("After 체크안함");
				}
				/*--------------------------------------------------------------*/
				/* FileOutputStream(.log) 객체 lFO 생성 */
				/*--------------------------------------------------------------*/
				prtLog("Log파일명 : " + loFile.getAbsolutePath());
				// System.out.println(">>>Log파일명 : "+loFile.getAbsolutePath());
				FileOutputStream lFO = new FileOutputStream(loFile.getAbsolutePath(), true);
				/*--------------------------------------------------------------*/
				/* 파일의 마지막에 CrLf */
				/*--------------------------------------------------------------*/
				sb.append("\n");
				/*--------------------------------------------------------------*/
				/* 파일 Make */
				/*--------------------------------------------------------------*/
				if (loFile.canWrite()) {
					lFO.write(sb.toString().getBytes());
					lFO.close();
					prtLog("endTran->Log파일에 write");
				} else {
					prtErr("endTran->Log파일에 write할 수 없습니다(canWrite=false)");
				}
			}
		} catch (Exception e) {
			prtErr("(Log만드는 중 오류발생)" + e);
			return 0;
		}
		prtLog("endTran->" + sb.toString());
		return 1;
	}

	public String getTloLogPath() {
		return lsTloLogPath;
	}

	public String getTloLogOnMPath() {
		return lsTloLogOnMPath;
	}

	/*--------------------------------------------------------------------------*/
	/* simulation에서 필요한 변수 */
	/*--------------------------------------------------------------------------*/
	public String getTloWriteTermH() {
		return lsTloWriteTermH;
	}

	public String getTloDataCnt() {
		return lsTloDataCnt;
	}

	public String getTloReadCnt() {
		return lsTloReadCnt;
	}

	public String getTloSrcLogFile() {
		return lsTloSrcLogFile;
	}

	public String getTloTimeSystem() {
		return lsTloTimeSystem;
	}

	/*--------------------------------------------------------------------------*/
	/* Test로 로그 만들 때의 폴더명. */
	/*--------------------------------------------------------------------------*/
	public void setTestDir(String asDir) {
		lsTestDir = asDir;
	}

	/*--------------------------------------------------------------------------*/
	/* Unique한 Key값. */
	/*--------------------------------------------------------------------------*/
	public String guidKey() {

		if (seqNumber.equals("9999"))
			seqNumber = "0000";

		this.seqNumber = String.valueOf((Integer.parseInt(seqNumber)) + 1);

		String leftPadding = "";
		for (int i = 0; i < 4 - this.seqNumber.length(); i++) {

			leftPadding += "0";
			// System.out.println("LeftPadding===>" + leftPadding);
		}

		this.seqNumber = leftPadding + this.seqNumber;

		// System.out.println("SEQUENCE===>" + this.seqNumber);

		String lsRand1 = "" + Math.abs(rand.nextInt());
		String lsRand2 = "" + Math.abs(rand.nextInt());

		if (lsRand1.length() < 2) {
			for (int i = 0; i < 2 - lsRand1.length(); i++) {
				lsRand1 = "0" + lsRand1;
			}
		}
		if (lsRand2.length() < 2) {
			for (int i = 0; i < 2 - lsRand2.length(); i++) {
				lsRand2 = "0" + lsRand2;
			}
		}

		// return (new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new
		// Date()))+lsRand1.substring(0,4)+lsRand2.substring(0,2)+lsTloServerID.substring(1,3);
		return (new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())) + lsRand1.substring(0, 2) + this.seqNumber
				+ lsTloServerID.substring(1, 3);

	}

	/*--------------------------------------------------------------------------*/
	/* 한글 깨어짐 방지를 위해 */
	/*--------------------------------------------------------------------------*/
	private static String asc2ksc(String asAscii) {
		try {
			return new String(asAscii.getBytes("8859_1"), "KSC5601");
		} catch (UnsupportedEncodingException e) {
			return asAscii;
		}
	}

	/*--------------------------------------------------------------------------*/
	/* 날짜 계산 함수 */
	/* asDateTime=기준날짜(yyyyMMddHHmm) */
	/* asWhen=연산상대(년,월,일,시,분) */
	/* aiMinute=가감 수치 */
	/*--------------------------------------------------------------------------*/
	private String calcDateTime(String asDateTime, String asWhen, int aiMinute) {
		try {
			int liWhen = 0;
			GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(asDateTime.substring(0, 4)),
					Integer.parseInt(asDateTime.substring(4, 6)), Integer.parseInt(asDateTime.substring(6, 8)),
					Integer.parseInt(asDateTime.substring(8, 10)), Integer.parseInt(asDateTime.substring(10, 12)),
					(asDateTime.length() > 12) ? Integer.parseInt(asDateTime.substring(12, 14)) : 0);
			gc.add(Calendar.MONTH, -1); /* Default */
			if ("YEAR".equals(asWhen)) {
				liWhen = Calendar.YEAR;
			} else if ("MONTH".equals(asWhen)) {
				liWhen = Calendar.MONTH;
			} else if ("DAY".equals(asWhen)) {
				liWhen = Calendar.DAY_OF_MONTH;
			} else if ("HOUR".equals(asWhen)) {
				liWhen = Calendar.HOUR;
			} else if ("MINUTE".equals(asWhen)) {
				liWhen = Calendar.MINUTE;
			} else if ("SECOND".equals(asWhen)) {
				liWhen = Calendar.SECOND;
			}
			gc.add(liWhen, aiMinute);
			return (new SimpleDateFormat("yyyyMMddHHmmss")).format(gc.getTime());
		} catch (Exception err) {
			prtLog("오류발생(calcDateTime) : " + err);
			return asDateTime;
		}
	}

	/*--------------------------------------------------------------------------*/
	/* 날짜 계산 함수 : 현재날짜 - 날짜 */
	/* asAfter=날짜(yyyyMMddHHmmss) */
	/* asLogTime=현재상대(yyyyMMddHHmmss) */
	/* 두날짜의 차이를 초단위 반환 */
	/*--------------------------------------------------------------------------*/
	// private int calcDate(String asAfter, String asLogTime)
	// {
	// //
	// }
	/*--------------------------------------------------------------------------*/
	/* TUnionLog Class의 Log */
	/*--------------------------------------------------------------------------*/
	private void prtLog(String psStr) {
		if ("true".equals(lsTloThisLogInd.toLowerCase()))
			System.out.println("■▒□TLO:>" + psStr);
	}

	/*--------------------------------------------------------------------------*/
	/* TUnionLog Class의 오류발생시 오류로그를 생성. */
	/*--------------------------------------------------------------------------*/
	private void prtErr(String psStr) /* 오류발생 */
	{
		try {
			/*------------------------------------------------------------------*/
			/* LogInd에 따른 Service Type */
			/*------------------------------------------------------------------*/
			String lsSvcType = lsLogInd;

			/*------------------------------------------------------------------*/
			/* 오류 내용 설정. */
			/*------------------------------------------------------------------*/
			String lsYYYYMMDDHHSS = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
			String lsErr = "[" + lsYYYYMMDDHHSS + "," + lsSvcType + "]" + psStr + "\n";
			/*------------------------------------------------------------------*/
			/* Error Log 생성 */
			/*------------------------------------------------------------------*/
			FileOutputStream lFOErr = new FileOutputStream(lsTloErrLogPath + lsSeparator + lsTloServiceName + "."
					+ lsTloServerCode + "." + lsYYYYMMDDHHSS.substring(0, 8) + ".log", true);
			/*------------------------------------------------------------------*/
			/* 파일 Make */
			/*------------------------------------------------------------------*/
			lFOErr.write(lsErr.getBytes());
			lFOErr.close();
		} catch (Exception err) {
			prtLog("■▒□오류로그 작성중 오류:" + err);
		}

		if ("true".equals(lsTloThisLogInd.toLowerCase()))
			System.out.println("■▒□TLO오류:>" + psStr);
	}

	public static void main(String[] args) {
		new TUnionLog();
	}

	public int setElementError(String asLogTime, StringBuffer sb) {
		// System.out.println("elmtName>>>>>"+elmtName);
		// System.out.println("elmtValue>>>>>"+elmtValue);
		/*----------------------------------------------------------------------*/
		/* ELEMENT name, value에 해당하는 문자열 저장 */
		/*----------------------------------------------------------------------*/
		sb.append(lsSep);

		prtErr("setElement->" + asLogTime);

		return 1;
	}

}
