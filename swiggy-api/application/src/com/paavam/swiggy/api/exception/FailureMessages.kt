package com.paavam.swiggy.api.exception

object FailureMessages {
    const val MESSAGE_MISSING_CREDENTIALS = "Required both username and password"
    const val MESSAGE_MISSING_TOKEN= "Required authentication token"

    const val MESSAGE_MISSING_ID = "ID Not received to process"
    const val MESSAGE_MISSING_FIELDS = "Empty fields detected"

    const val MESSAGE_FAILED = "Something went wrong!"
    const val MESSAGE_ACCESS_DENIED = "Access Denied!"
}