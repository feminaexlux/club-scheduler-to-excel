package net.feminaexlux.converter.clubschedulertoexcel.model;

import net.feminaexlux.converter.clubschedulertoexcel.external.EasySpeakConvertable;

import java.util.Date;

public class Speech implements Comparable<Speech>, EasySpeakConvertable {

	private final String speaker;
	private final String evaluator;
	private final String title;
	private final int workbook;
	private final int assignment;
	private final Date date;

	private Speech(final Builder builder) {
		this.speaker = builder.speaker;
		this.evaluator = builder.evaluator;
		this.title = builder.title;
		this.workbook = builder.workbook;
		this.assignment = builder.assignment;
		this.date = builder.date;
	}

	@Override
	public String toCsv() {
		return String.format("\"%s\", %d, %d, \"%s\", %5$td%5$tb%5$tY, \"%6$s\"",
				speaker,
				workbook,
				assignment,
				title,
				date,
				evaluator)
				.replace("_", " ")
				.replace("\"\"", "\"")
				.replace("\"null\"", "Unknown");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Speech that = (Speech) o;

		if (Double.compare(that.assignment, assignment) != 0) return false;
		if (workbook != that.workbook) return false;
		if (date != null ? !date.equals(that.date) : that.date != null) return false;
		if (evaluator != null ? !evaluator.equals(that.evaluator) : that.evaluator != null) return false;
		if (speaker != null ? !speaker.equals(that.speaker) : that.speaker != null) return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = speaker != null ? speaker.hashCode() : 0;
		result = 31 * result + (evaluator != null ? evaluator.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + workbook;
		result = 31 * result + assignment;
		result = 31 * result + (date != null ? date.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(final Speech that) {
		if (speaker == null) {
			return -1;
		}

		if (that.speaker == null) {
			return 1;
		}

		if (speaker.equals(that.speaker)) {
			if (workbook == that.workbook) {
				return assignment - that.assignment;
			}

			return workbook - that.workbook;
		}

		return speaker.compareTo(that.speaker);
	}

	public static class Builder {
		private String speaker;
		private String evaluator;
		private String title;
		private int workbook;
		private int assignment;
		private Date date;

		public Builder speaker(String speaker) {
			this.speaker = speaker;
			return this;
		}

		public Builder evaluator(String evaluator) {
			this.evaluator = evaluator;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder workbook(int workbook) {
			this.workbook = workbook;
			return this;
		}

		public Builder assignment(int assignment) {
			this.assignment = assignment;
			return this;
		}

		public Builder date(Date date) {
			this.date = date;
			return this;
		}

		public Speech build() {
			return new Speech(this);
		}

	}

}
