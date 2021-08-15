package com.paavam.exception

class BadRequestException(override val message: String) : Exception(message)

class RestaurantNotFoundException(override val message: String) : Exception(message)

class UnauthorizedException(override val message: String) : Exception(message)