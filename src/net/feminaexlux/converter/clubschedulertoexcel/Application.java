package net.feminaexlux.converter.clubschedulertoexcel;

import net.feminaexlux.converter.clubschedulertoexcel.model.Speech;
import net.feminaexlux.converter.clubschedulertoexcel.processor.MeetingProcessor;
import net.feminaexlux.converter.clubschedulertoexcel.util.StringUtil;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Application {

	private static final String END_OF_MEETING = "END_RECORD";

	public static void main(final String[] args) throws Exception {
		List<String> history = Files.readAllLines(Paths.get("History.dat"), Charset.defaultCharset());

		MeetingProcessor meetingProcessor = new MeetingProcessor();

		List<Speech> speeches = new ArrayList<>();
		List<String> meeting = new ArrayList<>();
		for (String piece : history) {
			if (StringUtil.isNotEmpty(piece)) {
				meeting.add(piece.trim());

				if (piece.trim().equals(END_OF_MEETING)) {
					speeches.addAll(meetingProcessor.getSpeechesFromMeeting(meeting));
					meeting.clear();
				}
			}
		}

		Collections.sort(speeches);
		for (Speech speech : speeches) {
			System.out.println(speech.toCsv());
		}
	}

}
