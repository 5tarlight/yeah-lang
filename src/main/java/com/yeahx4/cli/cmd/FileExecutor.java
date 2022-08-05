package com.yeahx4.cli.cmd;

import com.yeahx4.lang.YeahLangReader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Execute file from cli argument
 */
public class FileExecutor {
    /**
     * Execute .yeah file.
     *
     * @param key cli argument path. relative path will be automatically converted into absolute path.
     */
    public static void runFile(String key) {
        File file = new File(key);
        String path;

        try {
            path = absUrl(file.getAbsolutePath());
            YeahLangReader reader = new YeahLangReader(path);
            System.out.println(reader.readFile());
        } catch (Exception ex) {
            // TODO handle every exception
            ex.printStackTrace();
        }
    }

    /**
     * parse protocol-based relative url to absolute protocol-based url
     *
     * @param baseUrlString base url
     * @param urlString relative url being joined with baseUrlString
     * @return absolute protocol url
     * @throws MalformedURLException protocol is not provided or not valid
     * @throws URISyntaxException uri syntax is wrong
     */
    public static String absUrl(String baseUrlString, String urlString) throws MalformedURLException, URISyntaxException {
        if (urlString == null || urlString.trim().length() == 0)
            urlString = "";

        URL baseUrl = new URL(baseUrlString);
        URL url = new URL(baseUrl, urlString);
        urlString = url.toString().replaceAll("\\\\+", "/");
        url = new URL(urlString);
        String uri = url.getPath();
        String uriString = uri.replaceAll("/+", "/");
        urlString = url.toString().replaceAll(uri, uriString);
        int index = urlString.indexOf("/..");

        if (index < 0)
            return urlString;

        String urlStringLeft = urlString.substring(0, index) + "/";
        String urlStringRight = urlString.substring(index + 1);

        return absUrl(urlStringLeft, urlStringRight);
    }

    /**
     * parse non-protocol-based url to absolute url
     *
     * @param url url to beatify
     * @return absolute url
     * @throws URISyntaxException Unexpected url syntax
     */
    public static String absUrl(String url) throws URISyntaxException {
        try {
            return absUrl("http://" + url, null).substring(5);
        } catch (MalformedURLException ex) {
            return "";
        }
    }
}
