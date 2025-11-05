package org.cloudburstmc.protocol.bedrock.util;

import java.util.Map;

/**
 * @author Kevims KCodeYT
 */
public interface ValidationResult {

    boolean signed();

    Map<String, Object> rawIdentityClaims();

    IdentityClaims identityClaims();

}
