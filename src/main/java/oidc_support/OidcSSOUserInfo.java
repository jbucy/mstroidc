/*
 * package oidc_support;
 * 
 * import java.util.List;
 * 
 * import org.pac4j.oidc.profile.OidcProfile; import
 * com.microstrategy.auth.SSOUserInfo;
 * 
 * public class OidcSSOUserInfo implements SSOUserInfo { private static final
 * long serialVersionUID = -7806359799358590301L; private String userId; private
 * List<Group> groups; private String distinguishedName; private boolean
 * isValid; private String displayName; private String email;
 * 
 * public OidcSSOUserInfo(OidcProfile profile) { this.userId =
 * profile.getUsername(); this.groups = new ArrayList<Group>();
 * this.distinguishedName = ""; this.isValid = true; this.displayName =
 * profile.getDisplayName(); this.email = profile.getEmail(); }
 * 
 * @Override public String getUserId() { return this.userId; }
 * 
 * @Override public List<Group> getGroups() { return this.groups; }
 * 
 * @Override public String getDistinguishedName() { return
 * this.distinguishedName; }
 * 
 * @Override public boolean isValid() { return this.isValid; }
 * 
 * @Override public String getDisplayName() { return this.displayName; }
 * 
 * @Override public String getEmail() { return this.email; } }
 * 
 */