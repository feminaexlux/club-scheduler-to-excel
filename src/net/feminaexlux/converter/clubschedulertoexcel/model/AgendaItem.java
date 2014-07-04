package net.feminaexlux.converter.clubschedulertoexcel.model;

public class AgendaItem {

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
