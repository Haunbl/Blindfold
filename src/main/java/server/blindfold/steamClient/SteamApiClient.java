package server.blindfold.steamClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class SteamApiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${steam.url}")
    private String steamBaseUrl;
    @Value("${steam.token}")
    private String API_KEY; // 여기에 API 키를 삽입하세요.

    /**
     * 유저 프로파일 정보를 조회합니다.
     *
     * @param steamId 유저의 Steam ID
     * @return 유저 프로파일 정보 JSON
     */
    public JsonNode getPlayerSummaries(String steamId) {
        String url = String.format("%s/ISteamUser/GetPlayerSummaries/v2/?key=%s&steamids=%s",
            steamBaseUrl, API_KEY, steamId);
        try {
            String response = restTemplate.getForObject(url, String.class);
            log.info(response);
            return objectMapper.readTree(response).get("response").get("players").get(0);
        } catch (Exception ex) {
            log.error("Failed to get player summaries", ex);
            return null;
        }
    }

    /**
     * 유저가 소유한 게임 목록을 조회합니다.
     *
     * @param steamId 유저의 Steam ID
     * @return 유저 소유 게임 목록 JSON
     */
    public JsonNode getOwnedGames(String steamId) {
        String url = String.format("%s/IPlayerService/GetOwnedGames/v1/?key=%s&steamid=%s&include_appinfo=true",
            steamBaseUrl, API_KEY, steamId);
        try {
            String response = restTemplate.getForObject(url, String.class);
            return objectMapper.readTree(response).get("response");
        } catch (Exception ex) {
            log.error("Failed to get owned games", ex);
            return null;
        }
    }

    /**
     * 유저가 최근 플레이한 게임 목록을 조회합니다.
     *
     * @param steamId 유저의 Steam ID
     * @return 최근 플레이한 게임 목록 JSON
     */
    public JsonNode getRecentlyPlayedGames(String steamId) {
        String url = String.format("%s/IPlayerService/GetRecentlyPlayedGames/v1/?key=%s&steamid=%s",
            steamBaseUrl, API_KEY, steamId);
        try {
            String response = restTemplate.getForObject(url, String.class);
            return objectMapper.readTree(response).get("response");
        } catch (Exception ex) {
            log.error("Failed to get recently played games", ex);
            return null;
        }
    }
}