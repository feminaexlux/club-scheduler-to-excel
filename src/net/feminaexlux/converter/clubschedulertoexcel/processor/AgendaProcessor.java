package net.feminaexlux.converter.clubschedulertoexcel.processor;

import net.feminaexlux.converter.clubschedulertoexcel.model.AgendaItem;
import net.feminaexlux.converter.clubschedulertoexcel.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AgendaProcessor {

	private static final String DESCRIPTION_1 = "AI_DESC1";
	private static final String DESCRIPTION_2 = "AI_DESC2";
	private static final String END_OF_AGENDA_ITEM_MARKER = "END_AI";
	private static final String INDEX = "AI_IDX";
	private static final String NAME = "AI_NAME";
	private static final String SPACER = ": ";

	public List<AgendaItem> getAgendaItems(final List<String> agendaDetails) {
		if (CollectionUtil.isEmpty(agendaDetails)) {
			return Collections.emptyList();
		}

		List<AgendaItem> agendaItems = new ArrayList<>();
		Iterator<String> iterator = agendaDetails.iterator();

		List<String> agendaItemDetails = new ArrayList<>();
		while (iterator.hasNext()) {
			String agendaItemDetail = iterator.next();
			agendaItemDetails.add(agendaItemDetail);
			iterator.remove();

			if (agendaItemDetail.equalsIgnoreCase(END_OF_AGENDA_ITEM_MARKER)) {
				agendaItems.add(buildAgendaItem(agendaItemDetails));
				agendaItemDetails.clear();
			}
		}

		return agendaItems;
	}

	private AgendaItem buildAgendaItem(final List<String> lineItems) {
		AgendaItem.Builder agendaItemBuilder = new AgendaItem.Builder();

		for (String lineItem : lineItems) {
			int startOfValue = lineItem.lastIndexOf(SPACER) + 1;
			String value = lineItem.substring(startOfValue);

			if (lineItem.startsWith(NAME)) {
				agendaItemBuilder.name(value);
			} else if (lineItem.startsWith(DESCRIPTION_1)) {
				agendaItemBuilder.description1(value);
			} else if (lineItem.startsWith(DESCRIPTION_2)) {
				agendaItemBuilder.description2(value);
			} else if (lineItem.startsWith(INDEX)) {
				agendaItemBuilder.index(Integer.parseInt(value));
			}
		}


		return agendaItemBuilder.build();
	}

}
