package net.feminaexlux.converter.clubschedulertoexcel.model;

import net.feminaexlux.converter.clubschedulertoexcel.util.StringUtil;

public class AgendaItem implements Comparable<AgendaItem> {

	public static final String SPACER = ": ";

	private final int index;
	private final String description1;
	private final String description2;

	private AgendaItem(final Builder builder) {
		this.index = builder.index;
		this.description1 = builder.description1;
		this.description2 = builder.description2;
	}

	public int getIndex() {
		return index;
	}

	public String getTitle() {
		if (StringUtil.isNotEmpty(description1)) {
			return description1.replace("Title:", "").trim();
		}

		return null;
	}

	public int getWorkbook() {
		if (StringUtil.isNotEmpty(description2)) {
			if (description2.startsWith("Basic")) {
				return 1;
			} else if (description2.startsWith("Adv 2.")) {
				return 6;
			} else if (description2.startsWith("Adv 3.")) {
				return 8;
			} else if (description2.startsWith("Adv 9.")) {
				return 14;
			} else if (description2.startsWith("Adv 11.")) {
				return 24;
			} else if (description2.startsWith("Adv 12.")) {
				return 26;
			} else if (description2.equals("Off-Manual Speech")) {
				return 200;
			}
		}

		return 0;
	}

	public int getAssignment() {
		if (StringUtil.isNotEmpty(description2) && description2.contains(SPACER)) {
			String assignment = description2.substring(description2.indexOf(" ") + 1, description2.indexOf(SPACER)).trim();

			if (assignment.contains(".")) {
				assignment = assignment.substring(assignment.indexOf(".") + 1);
			}

			return Integer.parseInt(assignment);
		}

		return 0;
	}

	@Override
	public int compareTo(final AgendaItem that) {
		if (that == null) {
			return 1;
		}

		return that.index - this.index;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AgendaItem that = (AgendaItem) o;

		if (index != that.index) return false;
		if (description1 != null ? !description1.equals(that.description1) : that.description1 != null) return false;
		if (description2 != null ? !description2.equals(that.description2) : that.description2 != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = index;
		result = 31 * result + (description1 != null ? description1.hashCode() : 0);
		result = 31 * result + (description2 != null ? description2.hashCode() : 0);
		return result;
	}

	public static class Builder {
		private int index;
		private String description1;
		private String description2;

		public Builder index(final int index) {
			this.index = index;
			return this;
		}

		public Builder description1(final String description1) {
			this.description1 = description1;
			return this;
		}

		public Builder description2(final String description2) {
			this.description2 = description2;
			return this;
		}

		public AgendaItem build() {
			return new AgendaItem(this);
		}
	}

}
