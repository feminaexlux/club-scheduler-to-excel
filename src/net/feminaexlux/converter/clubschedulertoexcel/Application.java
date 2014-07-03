package net.feminaexlux.converter.clubschedulertoexcel;

import net.feminaexlux.converter.clubschedulertoexcel.model.Speech;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Application {

	public static void main(final String[] args) throws Exception {
		List<String> history = Files.readAllLines(Paths.get("History.dat"), Charset.defaultCharset());

		SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy");

		Speech.Builder speech1 = new Speech.Builder();
		Speech.Builder speech2 = new Speech.Builder();
		Speech.Builder speech3 = new Speech.Builder();

		Speech.Builder current = null;

		Set<Speech> speeches = new TreeSet<>();
		for (String piece : history) {
			if (hasValue(piece)) {
				if (piece.equals("END_AI")) {
					speeches.add(current.build());
					current = null;
					continue;
				}

				String[] pieces = piece.split(":");

				if (pieces.length == 1) {
					continue;
				}

				String value = pieces[1].trim();
				switch (pieces[0]) {
					case ("SCHED_ROLE"):
						String person = value.substring(value.indexOf(" ") + 1);
						if (value.startsWith("7")) {
							speech1.speaker(person);
						} else if (value.startsWith("8")) {
							speech2.speaker(person);
						} else if (value.startsWith("4")) {
							speech3.speaker(person);
						} else if (value.startsWith("14")) {
							speech1.evaluator(person);
						} else if (value.startsWith("15")) {
							speech2.evaluator(person);
						} else if (value.startsWith("6")) {
							speech3.evaluator(person);
						}

						break;
					case ("MEETING_DATE"):
						Date date = dateFormat.parse(value);
						speech1.date(date);
						speech2.date(date);
						speech3.date(date);
						break;
					case ("AI_NAME"):
						if (value.contains("3")) {
							current = speech3;
						} else if (value.contains("2")) {
							current = speech2;
						} else {
							current = speech1;
						}

						break;
					case ("AI_DESC1"):
						if (pieces.length > 2) {
							current.title("\"" + pieces[2].trim() + "\"");
						} else {
							System.out.println("Bad title: " + piece);
						}

						break;
					case ("AI_DESC2"):
						person = value.substring(value.indexOf(" ") + 1);
						if (value.contains("Basic")) {
							current.workbook(1);
						}

						try {
							current.assignment(Double.valueOf(person));
						} catch (NumberFormatException e) {
							System.out.println("Bad format: " + piece);
						}
						break;
					case ("END_RECORD"):
						speech1 = new Speech.Builder();
						speech2 = new Speech.Builder();
						speech3 = new Speech.Builder();
				}
			}
		}

		System.out.println("------------ SPEECHES " + speeches.size());

		for (Speech speech : speeches) {
			System.out.println(speech.toCsv());
		}
	}

	private static boolean hasValue(final String string) {
		return string != null && string.trim().length() > 0 && !string.startsWith("#");
	}

}
