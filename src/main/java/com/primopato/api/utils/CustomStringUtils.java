package com.primopato.api.utils;

import lombok.experimental.UtilityClass;

import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;

@UtilityClass
public class CustomStringUtils {

    private static final List<String> EXCECAO_INICIAL_MAIUSCULA = List.of(
            "o", "a", "os", "as", "um", "uma", "uns", "umas",
            "de", "da", "do", "das", "dos", "em", "no", "na", "nos", "nas",
            "por", "para", "com", "sem", "sob", "sobre", "até", "entre", "desde",
            "e", "ou", "mas", "nem", "que", "se", "como", "porque", "embora", "enquanto"
    );

    public static String formataIncialMaiuscula(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder resultado = new StringBuilder();
        BreakIterator iterator = BreakIterator.getWordInstance(new Locale("pt", "BR"));
        iterator.setText(input);
        int start = iterator.first();
        boolean primeiraPalavra = true;

        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String palavra = input.substring(start, end);
            String palavraTrim = palavra.trim();

            if (!palavraTrim.isEmpty() && Character.isLetterOrDigit(palavraTrim.charAt(0))) {
                if (primeiraPalavra || !EXCECAO_INICIAL_MAIUSCULA.contains(palavraTrim.toLowerCase())) {
                    resultado.append(Character.toUpperCase(palavraTrim.charAt(0)))
                            .append(palavraTrim.substring(1).toLowerCase());
                } else {
                    resultado.append(palavraTrim.toLowerCase());
                }
                primeiraPalavra = false;
            } else {
                resultado.append(palavra);
            }
        }

        return resultado.toString();
    }

    public static String coalesce(String... valores) {
        for (String v : valores) {
            if (v != null && !v.isBlank()) return v;
        }
        return null;
    }
}
