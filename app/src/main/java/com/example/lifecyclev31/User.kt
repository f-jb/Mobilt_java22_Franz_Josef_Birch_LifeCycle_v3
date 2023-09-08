package com.example.lifecyclev31

class User private constructor(user: UserBuilder) {
    private val name: String
    private val email: String
    private val dob: String
    private val driversLicense: Boolean
    private val hashedPassword: ByteArray
    private val salt: ByteArray

    init {
        this.name = user.name!!
        this.email = user.email!!
        this.dob = user.dob!!
        this.driversLicense = user.driversLicense!!
        this.hashedPassword = user.hashedPassword!!
        this.salt = user.salt!!
    }

    class UserBuilder {
        var name: String? = null
        var email: String? = null
        var dob: String? = null
        var driversLicense: Boolean? = null
        private var password: String? = null
        var hashedPassword: ByteArray? = null
        var salt: ByteArray? = null

        fun setName(name: String): UserBuilder {
            this.name = name
            return this
        }

        fun setEmail(email: String): UserBuilder {
            this.email = email
            return this
        }

        fun setDob(dob: String): UserBuilder {
            this.dob = dob
            return this
        }

        fun setDriversLicense(driversLicense: Boolean): UserBuilder {
            this.driversLicense = driversLicense
            return this
        }

        fun setPassword(password: String): UserBuilder {
            this.password = password
            return this
        }

        fun setHashedPassword(hashedPassword: ByteArray): UserBuilder {
            this.hashedPassword = hashedPassword
            return this
        }

        fun setSalt(salt: ByteArray): UserBuilder {
            this.salt = salt
            return this
        }

        fun build(): User {
            val bytePassword: ByteArray= Authentication.stringToByteArray(this.password!!)
            salt = Authentication.generateSalt()
            hashedPassword = Authentication.hashPw(bytePassword, salt!!)
            return User(this)

        }

        fun verify(): Boolean {
            return "" !in listOf(name, email, dob, driversLicense, password)
        }

    }

}