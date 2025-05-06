package com.dian.prueba.network

/**
 * Prueba de JWT
 * Para generar tokens
 */

import com.dian.prueba.utilities.Logger
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import java.util.*

/**
 * Esta no sirve, pero igualmente la dejo aquí para saber que se puede hacer
 */
fun prueba() {
    val logger = Logger()
    val currentTime = System.currentTimeMillis()
    val expirationTime = Date(currentTime + 864000000L)
    val claims = mapOf("sub" to "public_key", "exp" to expirationTime.time / 1000)
    val privateKey = "private_key"
    val base64Key = Base64.getEncoder().encodeToString(privateKey.toByteArray())

    val jwtToken = Jwts.builder()
        .claims(claims)
        .signWith(SignatureAlgorithm.HS512, base64Key)
        .compact()
    logger.debug(jwtToken, "Prueba JWT")
}

/**
 * Sirve
 */
fun prueba2(){
    val logger = Logger()
    val accessKey = "auzNN7V0aB30poSilNi15HCiE"
    val key = Keys.hmacShaKeyFor(Encoders.BASE64.encode(accessKey.toByteArray()).toByteArray())
    val now = Date()
    val jwt = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .claim("data", "flow")
        .setIssuedAt(now)
        .setExpiration(Date(now.time + 2 * 1000 * 60 * 60))
        .signWith(key)
        .compact()
    logger.debug(jwt, "Prueba JWT")
}

/**
 * Sirve
 */
fun generarToken(tipo: String) {
    val logger = Logger()
    val accessKey = "auzNN7V0aB30poSilNi15HCiE"
    val key = Keys.hmacShaKeyFor(Encoders.BASE64.encode(accessKey.toByteArray()).toByteArray())
    val now = Date()
    val expiracion = when (tipo) {
        "access" -> 5 * 60 * 1000 // 5 minutos
        "refresh" -> 7 * 24 * 60 * 60 * 1000 // 7 días
        else -> {
            logger.debug(tipo, "Tipo de token no válido")
            throw IllegalArgumentException("Tipo de token no válido")
        }
    }

    val jwt = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .claim("data", "flow")
        .claim("token_type", tipo)
        .setIssuedAt(now)
        .setExpiration(Date(now.time + expiracion))
        .signWith(key)
        .compact()

    logger.debug(jwt, "Prueba JWT")
}


