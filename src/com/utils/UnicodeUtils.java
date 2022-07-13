package com.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;

public class UnicodeUtils {

	public static final String ICON_PARAGRAPH = "\u00A7";
	
	public static List<String> wrapText(String text) {
		String doubleParagraph = UnicodeUtils.ICON_PARAGRAPH + UnicodeUtils.ICON_PARAGRAPH;
		String[] textParts = WordUtils.wrap(text, 42, doubleParagraph, true).split(doubleParagraph);
		return Arrays.asList(textParts);
	}
}
