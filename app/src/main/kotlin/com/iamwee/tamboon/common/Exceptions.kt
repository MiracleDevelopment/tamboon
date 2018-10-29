package com.iamwee.tamboon.common

class NetworkConnectionException : Exception() {

    override val message: String
    get() = "Network connection hasn't connected, please check your internet connection and try again."
}

class ServerException : Exception() {

    override val message: String
        get() = "There are something when wrong while connecting to server, please try again later."
}

class ClientErrorException(override val message: String): Exception()