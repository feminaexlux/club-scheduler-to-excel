package net.feminaexlux.converter.clubschedulertoexcel.processor;

import net.feminaexlux.converter.clubschedulertoexcel.model.Speech;
import net.feminaexlux.converter.clubschedulertoexcel.util.CollectionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeetingProcessor {

	private static final SimpleDateFormat MEETING_DATE_FORMAT = new SimpleDateFormat("M-d-yyyy");

	public List<Speech> getSpeechesFromMeeting(final List<String> meetingDetails) {
		if (CollectionUtil.isEmpty(meetingDetails)) {
			return Collections.emptyList();
		}

		List<Speech> speeches = new ArrayList<>();

		return speeches;
	}

}
