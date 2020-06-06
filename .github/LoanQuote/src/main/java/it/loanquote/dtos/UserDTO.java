package it.loanquote.dtos;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDTO extends User {

	private static final long serialVersionUID = -8706423761775352809L;
	private String uid;
	private String role;
	
	public UserDTO(String username, String password, boolean enabled, boolean accountNonExpired,
		      boolean credentialsNonExpired, boolean accountNonLocked,
		      Collection<? extends GrantedAuthority> authorities) {
		    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
		        authorities);
		  }

	  public UserDTO(String username, String password,
		      Collection<? extends GrantedAuthority> authorities) {
		    super(username, password, authorities);
		  }

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

		  
}
