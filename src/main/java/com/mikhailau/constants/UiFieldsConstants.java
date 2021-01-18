package com.mikhailau.constants;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UiFieldsConstants {
	public static final String EXPERTISE_NUMBER = "NUMBER";
	public static final String EXPERTISE_FINISH_DATE = "FINISH_DATE";
	public static final String EXPERTISE_TYPE = "EXP_TYPE";
	public static final String FOLDER_TO_SAVE = "FOLDER_TO_SAVE";
	public static final String TOTAL_HOURS_COUNT= "TIME";
	public static final String MATERIAL_TYPE= "MATERIAL_TYPE";
	public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public static final String EXP_TYPE_PREFIX = "судебной экспертизы ";

	public enum ExaminationType{
		HMS_COST("ХМС"),
		TSH_CANN("ТСХ КОНОПЛЯ"),
		TSH_PAPAVER("ТСХ МАК"),
		OSMOTR("ОСМОТР ВЕЩДОКОВ");
		private String russianTranscr;

		ExaminationType(String russianTranscr) {
			this.russianTranscr = russianTranscr;
		}

		public String getRussianTranscr() {
			return russianTranscr;
		}
	}

	public enum InvestigationType{
		MAT_ADMINISTRATIVE("Материал проверки"),MAT_UD("Уголовное дело");
		private String russianTranscr;

		InvestigationType(String russianTranscr) {
			this.russianTranscr = russianTranscr;
		}

		public String getRussianTranscr() {
			return russianTranscr;
		}

		public static InvestigationType getByName(String name){
			return Optional.ofNullable(name)
					.map(UiFieldsConstants.InvestigationType::valueOf)
					.orElse(UiFieldsConstants.InvestigationType.MAT_ADMINISTRATIVE);
		}
	}

	public enum ExpertiseType{
		DRUGS("наркотических средств и психотропных веществ, их аналогов и прекурсоров, " +
				"сильнодействующих веществ"),
		SSG("спиртосодержащих жидкостей");
		private String russianTranscr;

		ExpertiseType(String russianTranscr) {
			this.russianTranscr = russianTranscr;
		}

		public String getRussianTranscr() {
			return russianTranscr;
		}

		@Override
		public String toString() {
			return russianTranscr;
		}
	}




}
