package net.feminaexlux.converter.clubschedulertoexcel.processor;

import net.feminaexlux.converter.clubschedulertoexcel.model.AgendaItem;
import net.feminaexlux.converter.clubschedulertoexcel.model.AgendaItemType;
import net.feminaexlux.converter.clubschedulertoexcel.model.Speech;
import net.feminaexlux.converter.clubschedulertoexcel.util.CollectionUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MeetingProcessor {

	private static final SimpleDateFormat MEETING_DATE_FORMAT = new SimpleDateFormat("M-d-yyyy");

	public List<Speech> getSpeechesFromMeeting(final List<String> meetingDetails) throws ParseException {
		if (CollectionUtil.isEmpty(meetingDetails)) {
			return Collections.emptyList();
		}

		Speech.Builder speech1 = new Speech.Builder();
		Speech.Builder speech2 = new Speech.Builder();
		Speech.Builder speech3 = new Speech.Builder();

		Iterator<String> meetingIterator = meetingDetails.iterator();
		while (meetingIterator.hasNext()) {
			String meetingDetail = meetingIterator.next();

			int nameIndex = meetingDetail.lastIndexOf(" ") + 1;
			String value = nameIndex > 0 ? meetingDetail.substring(nameIndex) : meetingDetail;

			if (meetingDetail.startsWith("MEETING_DATE")) {
				Date date = MEETING_DATE_FORMAT.parse(value);
				speech1.date(date);
				speech2.date(date);
				speech3.date(date);
			}

			// Speakers
			if (meetingDetail.startsWith("SCHED_ROLE: 7")) {
				speech1.speaker(value);
			} else if (meetingDetail.startsWith("SCHED_ROLE: 8")) {
				speech2.speaker(value);
			} else if (meetingDetail.startsWith("SCHED_ROLE: 4")) {
				speech3.speaker(value);
			}

			// Evaluators
			if (meetingDetail.startsWith("SCHED_ROLE: 14")) {
				speech1.evaluator(value);
			} else if (meetingDetail.startsWith("SCHED_ROLE: 15")) {
				speech2.evaluator(value);
			} else if (meetingDetail.startsWith("SCHED_ROLE: 6")) {
				speech3.evaluator(value);
			}

			if (meetingDetail.equals("AGENDA_DEFN_DATA")) {
				break;
			}

			meetingIterator.remove();
		}

		AgendaProcessor agendaProcessor = new AgendaProcessor();
		Map<AgendaItemType, AgendaItem> agendaItems = agendaProcessor.getAgendaItems(meetingDetails);

		List<Speech> speeches = new ArrayList<>();
		if (agendaItems.containsKey(AgendaItemType.SPEECH_1)) {
			speeches.add(fillSpeechWithAgendaDetails(speech1, agendaItems.get(AgendaItemType.SPEECH_1)));
		}

		if (agendaItems.containsKey(AgendaItemType.SPEECH_2)) {
			speeches.add(fillSpeechWithAgendaDetails(speech2, agendaItems.get(AgendaItemType.SPEECH_2)));
		}

		if (agendaItems.containsKey(AgendaItemType.SPEECH_3)) {
			speeches.add(fillSpeechWithAgendaDetails(speech3, agendaItems.get(AgendaItemType.SPEECH_3)));
		}

		return speeches;
	}

	private Speech fillSpeechWithAgendaDetails(final Speech.Builder speechBuilder, final AgendaItem agendaItem) {
		return speechBuilder
				.title(agendaItem.getTitle())
				.workbook(agendaItem.getWorkbook())
				.assignment(agendaItem.getAssignment())
				.build();
	}

}
