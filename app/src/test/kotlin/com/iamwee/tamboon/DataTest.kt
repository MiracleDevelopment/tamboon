package com.iamwee.tamboon

import com.iamwee.tamboon.data.Charity
import com.iamwee.tamboon.data.DonationRequest

object DataTest {

    val charities = listOf(
            Charity(0, "Ban Khru Noi", "http://rkdretailiq.com/news/img-corporate-baankrunoi.jpg"),
            Charity(1, "Habitat for Humanity Thailand", "http://www.adamandlianne.com/uploads/2/2/1/6/2216267/3231127.gif"),
            Charity(2, "Paper Ranger", "https://myfreezer.files.wordpress.com/2007/06/paperranger.jpg"),
            Charity(3, "Makhampom", "http://www.makhampom.net/makhampom/ppcms/uploads/UserFiles/Image/Thai/T14Publice/2554/January/Newyear/logoweb.jpg")
    )

    val correctCard = DonationRequest(
        name = "John Weak",
        token = "token_12d1wl_dakl;sdk",
        amount = 50000
    )

}