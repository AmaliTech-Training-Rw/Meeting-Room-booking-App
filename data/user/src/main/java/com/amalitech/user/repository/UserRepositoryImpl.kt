package com.amalitech.user.repository

import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.models.User
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRepositoryImpl(
    private val dao: UserDao,
) : UserRepository {
    override suspend fun getUser(email: String): UserDto {
        return dao.getUser(email)
    }

    override suspend fun deleteUser(user: UserDto) {
        dao.deleteUser(user)
    }

    override suspend fun insertUser(user: UserDto) {
        dao.insertUser(user)
    }

    override suspend fun getUsers(): Flow<User> = flowOf(
        User(
            "1",
            "https://fastly.picsum.photos/id/433/200/200.jpg?hmac=dBn6DDBngOA94Grm3jfIJNDtv08GorUvB0zMeAw0Jfs",
            "Jack Danny",
            "jd6767@gmail.com",
            true
        ),
        User(
            "2",
            "https://fastly.picsum.photos/id/98/200/200.jpg?hmac=QiHfqn1VEz1-KB7Wpb5iUbtrmTV8s2e3DJFb4HEp-y0",
            "Jameson Whiskers",
            "jamie4545@gmail.com",
            false
        ),
        User(
            "3",
            "https://fastly.picsum.photos/id/1076/200/200.jpg?hmac=KTOq4o7b6rXzwd8kYN0nWrPIeKI97mzxBdWhnn-o-Nc",
            "Mary Poppins",
            "popmary@gmail.com",
            true
        ),
        User(
            "4",
            "https://fastly.picsum.photos/id/961/200/200.jpg?hmac=gHwrXvhjUL97oGKmAYQn508wdQ_V5sE9P64erzR-Ork",
            "John Smith",
            "johnsmith34@gmail.com",
            true
        ),
        User(
            "5",
            "https://fastly.picsum.photos/id/866/200/200.jpg?hmac=i0ngmQOk9dRZEzhEosP31m_vQnKBQ9C19TBP1CGoIUA",
            "Katogo Mere",
            "katogo@gmail.com",
            false
        ),
        User(
            "6",
            "https://fastly.picsum.photos/id/433/200/200.jpg?hmac=dBn6DDBngOA94Grm3jfIJNDtv08GorUvB0zMeAw0Jfs",
            "Kato Danny",
            "kato@gmail.com",
            true
        ),
        User(
            "7",
            "https://fastly.picsum.photos/id/98/200/200.jpg?hmac=QiHfqn1VEz1-KB7Wpb5iUbtrmTV8s2e3DJFb4HEp-y0",
            "Sonia John",
            "sonia@gmail.com",
            false
        ),
        User(
            "8",
            "https://fastly.picsum.photos/id/1076/200/200.jpg?hmac=KTOq4o7b6rXzwd8kYN0nWrPIeKI97mzxBdWhnn-o-Nc",
            "Elon Msk",
            "elon@gmail.com",
            true
        ),
        User(
            "9",
            "https://fastly.picsum.photos/id/961/200/200.jpg?hmac=gHwrXvhjUL97oGKmAYQn508wdQ_V5sE9P64erzR-Ork",
            "Hesus Kovid",
            "kovid@gmail.com",
            true
        ),
        User(
            "10",
            "https://fastly.picsum.photos/id/866/200/200.jpg?hmac=i0ngmQOk9dRZEzhEosP31m_vQnKBQ9C19TBP1CGoIUA",
            "Some Mane",
            "mane@gmail.com",
            false
        )
    )

    override suspend fun addUser(user: User) {
        // TODO: connect to the data source
    }

    override suspend fun updateProfile(profile: Profile) {
        //TODO("Not yet implemented")
    }
//    override suspend fun updateProfile(profile: Profile) {
//        // TODO("Save in the API and use the profile"
//        //  "url to save into the local DB, use transactions")
//    }
}
