package com.databps.bigdaf.admin.security.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * @author merlin
 * @create 2017-08-02 下午1:44
 */
public class SSOAdmin implements UserDetails, CredentialsContainer {
  private static final long serialVersionUID = 420L;
  private String password;
  private final String username;
  private final String cmpyId;
  private final String id;
  private final Set<GrantedAuthority> authorities;
  private final boolean accountNonExpired;
  private final boolean accountNonLocked;
  private final boolean credentialsNonExpired;
  private final boolean enabled;

  public SSOAdmin(String username, String cmpyId,String id,String password, Collection<? extends GrantedAuthority> authorities) {
    this(username, cmpyId,id,password, true, true, true, true, authorities);
  }

  public SSOAdmin(String username, String cmpyId,String id,String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    if(username != null && !"".equals(username) && password != null && cmpyId!=null) {
      this.username = username;
      this.password = password;
      this.cmpyId=cmpyId;
      this.id=id;
      this.enabled = enabled;
      this.accountNonExpired = accountNonExpired;
      this.credentialsNonExpired = credentialsNonExpired;
      this.accountNonLocked = accountNonLocked;
      this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    } else {
      throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
    }
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public String getPassword() {
    return this.password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getCmpyId(){
    return this.cmpyId;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  public void eraseCredentials() {
    this.password = null;
  }

  public String getId() {
    return id;
  }

  private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
    Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
    SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new AuthorityComparator());
    Iterator var2 = authorities.iterator();

    while(var2.hasNext()) {
      GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
      Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
      sortedAuthorities.add(grantedAuthority);
    }

    return sortedAuthorities;
  }

  public boolean equals(Object rhs) {
    return rhs instanceof SSOAdmin
        ?this.username.equals(((SSOAdmin)rhs).username):false;
  }

  public int hashCode() {
    return this.username.hashCode();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(super.toString()).append(": ");
    sb.append("Username: ").append(this.username).append("; ");
    sb.append("CmpyId: ").append(this.cmpyId).append("; ");
    sb.append("Password: [PROTECTED]; ");
    sb.append("Enabled: ").append(this.enabled).append("; ");
    sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
    sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
    sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
    if(!this.authorities.isEmpty()) {
      sb.append("Granted Authorities: ");
      boolean first = true;
      Iterator var3 = this.authorities.iterator();

      while(var3.hasNext()) {
        GrantedAuthority auth = (GrantedAuthority)var3.next();
        if(!first) {
          sb.append(",");
        }

        first = false;
        sb.append(auth);
      }
    } else {
      sb.append("Not granted any authorities");
    }

    return sb.toString();
  }

  public static UserBuilder withUsername(String username,String cmpyId) {
    return (new UserBuilder()).username(username).cmpyId(cmpyId);
  }

  public static class UserBuilder {
    private String username;
    private String password;
    private String cmpyId;
    private String id;
    private List<GrantedAuthority> authorities;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean disabled;

    private UserBuilder() {
    }

    private UserBuilder username(String username) {
      Assert.notNull(username, "username cannot be null");
      this.username = username;
      return this;
    }
    private UserBuilder cmpyId(String cmpyId) {
      Assert.notNull(cmpyId, "cmpyId cannot be null");
      this.cmpyId = cmpyId;
      return this;
    }

    private UserBuilder id(String id) {
      Assert.notNull(cmpyId, "id cannot be null");
      this.id = id;
      return this;
    }

    public UserBuilder password(String password) {
      Assert.notNull(password, "password cannot be null");
      this.password = password;
      return this;
    }

    public UserBuilder roles(String... roles) {
      List<GrantedAuthority> authorities = new ArrayList(roles.length);
      String[] var3 = roles;
      int var4 = roles.length;

      for(int var5 = 0; var5 < var4; ++var5) {
        String role = var3[var5];
        Assert.isTrue(!role.startsWith("ROLE_"), role + " cannot start with ROLE_ (it is automatically added)");
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
      }

      return this.authorities((List)authorities);
    }

    public UserBuilder authorities(GrantedAuthority... authorities) {
      return this.authorities(Arrays.asList(authorities));
    }

    public UserBuilder authorities(List<? extends GrantedAuthority> authorities) {
      this.authorities = new ArrayList(authorities);
      return this;
    }

    public UserBuilder authorities(String... authorities) {
      return this.authorities(AuthorityUtils.createAuthorityList(authorities));
    }

    public UserBuilder accountExpired(boolean accountExpired) {
      this.accountExpired = accountExpired;
      return this;
    }

    public UserBuilder accountLocked(boolean accountLocked) {
      this.accountLocked = accountLocked;
      return this;
    }

    public UserBuilder credentialsExpired(boolean credentialsExpired) {
      this.credentialsExpired = credentialsExpired;
      return this;
    }

    public UserBuilder disabled(boolean disabled) {
      this.disabled = disabled;
      return this;
    }

    public UserDetails build() {
      return new SSOAdmin(this.username,this.cmpyId, this.id,this.password, !this.disabled, !this.accountExpired, !this.credentialsExpired, !this.accountLocked, this.authorities);
    }
  }

  private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
    private static final long serialVersionUID = 420L;

    private AuthorityComparator() {
    }

    public int compare(GrantedAuthority g1, GrantedAuthority g2) {
      return g2.getAuthority() == null?-1:(g1.getAuthority() == null?1:g1.getAuthority().compareTo(g2.getAuthority()));
    }
  }
}
