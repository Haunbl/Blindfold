package server.blindfold.steamClient.application;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.blindfold.steamClient.SteamApiClient;

@Service
@RequiredArgsConstructor
public class SteamService {
    private final SteamApiClient steamApiClient;
    /**
     * Steam ID를 사용해 유저 정보를 가져옵니다.
     */
    public JsonNode getUserProfile(String steamId) {
        return steamApiClient.getPlayerSummaries(steamId);
    }

    /**
     * Steam ID를 사용해 소유 게임 목록을 가져옵니다.
     */
    public JsonNode getUserOwnedGames(String steamId) {
        return steamApiClient.getOwnedGames(steamId);
    }

    /**
     * Steam ID를 사용해 최근 플레이한 게임 목록을 가져옵니다.
     */
    public JsonNode getRecentlyPlayedGames(String steamId) {
        return steamApiClient.getRecentlyPlayedGames(steamId);
    }

}
