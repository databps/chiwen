package com.databps.bigdaf.admin.security.kerberos;

import org.apache.commons.lang.StringUtils;
import org.apache.directory.server.kerberos.shared.crypto.encryption.KerberosKeyFactory;
import org.apache.directory.server.kerberos.shared.keytab.Keytab;
import org.apache.directory.server.kerberos.shared.keytab.KeytabEntry;
import org.apache.directory.server.kerberos.shared.store.PrincipalStoreEntry;
import org.apache.directory.server.kerberos.shared.store.PrincipalStoreEntryModifier;
import org.apache.directory.shared.kerberos.KerberosTime;
import org.apache.directory.shared.kerberos.codec.types.EncryptionType;
import org.apache.directory.shared.kerberos.components.EncryptionKey;

import javax.security.auth.kerberos.KerberosPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class KerberosUtils {


  public static PrincipalStoreEntry getEntry(String principalName, String passPhrase,boolean disabled ) {
    KerberosPrincipal clientPrincipal = new KerberosPrincipal( principalName );

    PrincipalStoreEntryModifier modifier = new PrincipalStoreEntryModifier();
    modifier.setPrincipal( clientPrincipal );

    Map<EncryptionType, EncryptionKey> keyMap = KerberosKeyFactory.getKerberosKeys( principalName, passPhrase );

    modifier.setKeyMap(keyMap);
    
    modifier.setDisabled(disabled);

    return modifier.getEntry();
  }

  public static PrincipalStoreEntry getNullKeyEntry(String principalName) {
    KerberosPrincipal clientPrincipal = new KerberosPrincipal( principalName );

    PrincipalStoreEntryModifier modifier = new PrincipalStoreEntryModifier();
    modifier.setPrincipal( clientPrincipal );

    return modifier.getEntry();
  }


  public static Keytab createKeytab(String principalName, String principalPwd,Set<EncryptionType> ciphers) {
    return createKeytab(principalName, principalPwd, ciphers,null);

  }

  
  public static Keytab createKeytab(String principalName, String principalPwd,Set<EncryptionType> ciphers, Integer keyVersion) {

    if (StringUtils.isEmpty(principalName)) {
      throw new RuntimeException("Failed to create keytab file, missing principal");
    }

    if (StringUtils.isBlank(principalPwd)) {
      throw new RuntimeException(String.format("Failed to create keytab file for %s, missing password", principalPwd));
    }

    List<KeytabEntry> keytabEntries = new ArrayList<>();
    Keytab keytab = new Keytab();

    // Create a set of keys and relevant keytab entries
    Map<EncryptionType, EncryptionKey> keys = KerberosKeyFactory.getKerberosKeys(principalName, principalPwd,ciphers);

    if (keys != null) {
      keyVersion = (keyVersion == null) ? 0 : keyVersion;
      KerberosTime timestamp = new KerberosTime();

      for (EncryptionKey encryptionKey : keys.values()) {
        keytabEntries.add(new KeytabEntry(principalName, 1, timestamp, keyVersion.byteValue(), encryptionKey));
      }

      keytab.setEntries(keytabEntries);
    }

    return keytab;
  }
}
