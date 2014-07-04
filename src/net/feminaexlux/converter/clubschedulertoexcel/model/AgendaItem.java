package net.feminaexlux.converter.clubschedulertoexcel.model;

public class AgendaItem implements Comparable<AgendaItem> {

	private final int index;
	private final String name;
	private final String description1;
	private final String description2;

	private AgendaItem(final Builder builder) {
		this.index = builder.index;
		this.name = builder.name;
		this.description1 = builder.description1;
		this.description2 = builder.description2;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public String getDescription1() {
		return description1;
	}

	public String getDescription2() {
		return description2;
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
		if (name != null ? !name.equals(that.name) : that.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = index;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description1 != null ? description1.hashCode() : 0);
		result = 31 * result + (description2 != null ? description2.hashCode() : 0);
		return result;
	}

	public static class Builder {
		private int index;
		private String name;
		private String description1;
		private String description2;

		public Builder index(final int index) {
			this.index = index;
			return this;
		}

		public Builder name(final String name) {
			this.name = name;
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
