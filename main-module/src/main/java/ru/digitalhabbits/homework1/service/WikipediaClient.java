package ru.digitalhabbits.homework1.service;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import ru.digitalhabbits.homework1.dto.WikipediaResponseDto;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.slf4j.LoggerFactory.getLogger;

public class WikipediaClient {

    private static final Logger logger = getLogger(WikipediaClient.class);

    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {

        String result = sendGet(prepareSearchUrl(searchString));

        Gson gson = new Gson();

        logger.debug(result);

        WikipediaResponseDto wikipediaResponseDto = gson.fromJson(result, WikipediaResponseDto.class);

        if (wikipediaResponseDto == null) {
            return "";
        }

        return wikipediaResponseDto.getExtractText();
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String sendGet(URI uri) {
        HttpGet request = new HttpGet(uri);

        String result = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {

                result = EntityUtils.toString(entity);
            }

        } catch (IOException exception) {
            logger.error(exception.getMessage(), exception);
        }

        return result;
    }
}
