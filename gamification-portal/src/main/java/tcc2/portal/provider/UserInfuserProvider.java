package tcc2.portal.provider;

import org.springframework.stereotype.Service;

import tcc2.portal.provider.details.UsuarioUserInfUser;

import com.google.gson.Gson;
import com.userinfuser.client.UserInfuser;
import com.userinfuser.client.WidgetType;

@Service
public class UserInfuserProvider {

	private static final String EMAIL_LIDER = "hm1rafael@gmail.com";
	private final UserInfuser userInfuserClient = new UserInfuser(EMAIL_LIDER, "9195cd27-b1b0-4269-b89a-40d351233210", false, false, false, true);
	private final Gson gson = new Gson();

	public UsuarioUserInfUser getUser(String email) {
		String userInfo = this.userInfuserClient.getUserInfo(email);
		UsuarioUserInfUser usuario = this.gson.fromJson(userInfo, UsuarioUserInfUser.class);
		return usuario;
	}

	public boolean addOrUpdateUser(String email) {
		return this.userInfuserClient.updateUser(email,email,"", "");
	}

	public String getLeaderBoard() {
		return this.userInfuserClient.getWidget(EMAIL_LIDER, WidgetType.LEADERBOARD);
	}
	
	public String getThrophyCase(String email) {
		return this.userInfuserClient.getWidget(email, WidgetType.TROPHY_CASE);
	}

	public String getRanking(String username) {
		return this.userInfuserClient.getWidget(username, WidgetType.RANK);
	}

	public void addPoints(Integer pontuacao, String email) {
		this.userInfuserClient.awardPoints(email, pontuacao);
	}
	
	public void addBadge(String badgeId, String email) {
		this.userInfuserClient.awardBadge(email, badgeId);
	}

}
