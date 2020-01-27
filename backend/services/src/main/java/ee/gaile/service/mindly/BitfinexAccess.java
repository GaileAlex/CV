package ee.gaile.service.mindly;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * receiving and parsing requests with Bitfinex
 */
@Service
@EnableScheduling
public class BitfinexAccess {
    private HashMap<String, BigDecimal> currencyMap = new HashMap<>();

    /**
     * @return currency value
     * @throws IOException
     */
    @Scheduled(fixedRate = 60000)
    private void getPrice() throws IOException {
        currencyMap.put("Ethereum", getPrice("Ethereum"));
        currencyMap.put("Ripple", getPrice("Ripple"));
        currencyMap.put("Bitcoin", getPrice("Bitcoin"));
    }

    private BigDecimal getPrice(String currency) throws IOException {
        switch (currency) {
            case ("Ethereum"):
                currency = "ETHEUR";
                break;
            case ("Ripple"):
                currency = "XRPUSD";
                break;
            default:
                currency = "BTCEUR";
                break;
        }
        String urlBitfinex = "https://api.bitfinex.com/v1/book/" + currency;
        BigDecimal bitfinexRate = new BigDecimal(getDataByUrl(urlBitfinex)
                .replaceAll(".*?([\\d.]+).*", "$1"));

        //Ripple not sold for euros, but it is not accurate. We receive in dollars and we translate in euro at the rate.
        if (currency.equals("XRPUSD")) {
            BigDecimal usdEur = new BigDecimal(getDataByUrl("https://api.exchangeratesapi.io/latest?symbols=USD")
                    .replaceAll(".*?([\\d.]+).*", "$1"));

            bitfinexRate = bitfinexRate.divide(usdEur, 2, BigDecimal.ROUND_HALF_UP);
        }
        return bitfinexRate;
    }

    /**
     * @param url site
     * @return raw response from site
     * @throws IOException
     */
    private String getDataByUrl(String url) throws IOException {
        URL surl = new URL(url);
        URLConnection request = surl.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement element = jp.parse(new InputStreamReader((InputStream) request.getContent(), StandardCharsets.UTF_8));
        JsonObject obj = element.getAsJsonObject();

        return obj.toString();
    }

    public BigDecimal getCurrency(String currency) {
        return currencyMap.get(currency);
    }
}

