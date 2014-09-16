package tcc2.portal.provider.details;

import java.util.Collection;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioUserInfUser implements UserDetails {

    private String status;
    private String is_enabled;
    private long points;
    private String user_id;
    private String profile_img;
    private Boolean habilitado;
    private int errcode;
    private long id;

    public String getStatus() {
	return this.status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public boolean isIs_enabled() {
	return isHabilitado(this.is_enabled);
    }

    public boolean isHabilitado(String habilitado) {
	if (this.habilitado == null) {
	    this.habilitado = BooleanUtils.toBoolean(habilitado);
	}
	return this.habilitado;
    }

    public long getPoints() {
	return this.points;
    }

    public void setPoints(long points) {
	this.points = points;
    }

    public String getUser_id() {
	return this.user_id;
    }

    public void setUser_id(String user_id) {
	this.user_id = user_id;
    }

    public String getProfile_img() {
	return this.profile_img;
    }

    public void setProfile_img(String profile_img) {
	this.profile_img = profile_img;
    }

    public int getErrcode() {
	return this.errcode;
    }

    public void setErrcode(int errcode) {
	this.errcode = errcode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return null;
    }

    @Override
    public String getPassword() {
	return null;
    }

    @Override
    public String getUsername() {
	return this.user_id;
    }

    @Override
    public boolean isAccountNonExpired() {
	return isHabilitado(this.is_enabled);
    }

    @Override
    public boolean isAccountNonLocked() {
	return isHabilitado(this.is_enabled);
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return isHabilitado(this.is_enabled);
    }

    @Override
    public boolean isEnabled() {
	return isHabilitado(this.is_enabled);
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getId() {
	return this.id;
    }

}
