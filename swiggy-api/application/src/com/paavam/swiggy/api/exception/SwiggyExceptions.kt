package com.paavam.swiggy.api.exception

class BadRequestException(override val message: String) : Exception(message)

class UnauthorizedException(override val message: String) : Exception(message)

class RestaurantNotFoundException(override val message: String) : Exception(message)

class FoodNotFoundException(override val message: String) : Exception(message)

class OfferNotFoundException(override val message: String) : Exception(message)

class OfferRestaurantMissingLinkException(override val message: String) : Exception(message)