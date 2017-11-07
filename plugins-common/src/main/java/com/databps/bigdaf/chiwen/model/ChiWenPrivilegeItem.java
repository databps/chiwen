package com.databps.bigdaf.chiwen.model;

import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by lgc on 17-7-17.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChiWenPrivilegeItem implements java.io.Serializable{

  private static final long serialVersionUID = 8212425503661549328L;
  private Set<String> users ;
  private Set<String> groups;
  private List<Access> accesses;

  public Set<String> getUsers() {
    return users;
  }

  public void setUsers(Set<String> users) {
    this.users = users;
  }

  public Set<String> getGroups() {
    return groups;
  }

  public void setGroups(Set<String> groups) {
    this.groups = groups;
  }

  public List<Access> getAccesses() {
    return accesses;
  }

  public void setAccesses(List<Access> accesses) {
    this.accesses = accesses;
  }
}
