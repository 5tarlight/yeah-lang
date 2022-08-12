package com.yeahx4.cli.cmd;

import com.yeahx4.lang.InvalidYeahFileException;
import com.yeahx4.lang.YeahLangReader;
import com.yeahx4.lang.exe.InvalidYeahSyntaxException;
import com.yeahx4.lang.exe.YeahLangParser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Execute file from cli argument
 */
public final class FileExecutor {
    /**
     * Execute .yeah file.
     *
     * @param key cli argument path. relative path will be automatically converted into absolute path.
     * @throws InvalidYeahSyntaxException content of file is not valid yeah syntax
     * @throws InvalidYeahFileException file of given path is not exist or invalid extension
     */
    public static void runFile(String key) throws InvalidYeahFileException, InvalidYeahSyntaxException {
        File file = new File(key);
        String path;

        try {
            path = absUrl(file.getAbsolutePath());
            YeahLangReader reader = new YeahLangReader(path);
            String content = reader.readFile();
            YeahLangParser parser = new YeahLangParser();
            parser.parse(content, path);
        } catch (URISyntaxException uri) {
            throw new InvalidYeahFileException(key, uri.getReason());
        } catch (IOException io) {
            throw new InvalidYeahFileException(key, "Unable to locate .yeah file");
        }
    }

    /**
     * parse protocol-based relative url to absolute protocol-based url
     *
     * {@code baseUrlString} must start with protocol
     * <pre>
     * FileExecutor.absUrl("test/foo/com", null)
     * </pre>
     * Up will throw {@link MalformedURLException}.
     * And protocol must well-known and valid.
     * <pre>
     * FileExecutor.absUrl("http://yeah/com", null)
     * </pre>
     * Up is ok.
     * <pre>
     * FileExecutor.absUrl("yeah://yeaaaah", null)
     * </pre>
     * Up will cause {@link MalformedURLException}
     *
     * @param baseUrlString base url
     * @param urlString relative url being joined with baseUrlString
     * @return absolute protocol url
     * @throws MalformedURLException protocol is not provided or not valid
     */
    public static String absUrl(String baseUrlString, String urlString) throws MalformedURLException {
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
