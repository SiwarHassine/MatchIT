package com.ms.msuser.RouteValidator;

public enum RecruteurRoute {
    /* URLusers */
    USER_GET_BY_ID("/user/"),
    USER_UPDATE("/user/Update"),
    USER_DELETE("/user/Delete"),
    USER_NEW_PASSWORD("/user/NewPassword"),
    USER_ALL_USERS("/user/AllUsers"),
    USER_ROLES("/user/role/CANDIDAT"),
    /* URLOffre */
    OFFER_LIST("/offerList"),
	OFFER_PAGINATION("/pagination"),
	OFFER_ADD("/saveOffer"),
	OFFER_UPDATE("/updateOffer"),
	OFFER_GETFOFFER("/getOfferById"),
	OFFER_DELETE("/deleteOffer"),
	OFFER_NOTEXPIRED("/getOfferNotExpired"),
	OFFER_BYSTATUS("/paginationAndSort"),
	/* URLCalculator */
	APPLICATION_GETALL("/allApplication"),
	APPLICATION_PAGINATED("/paginationApplication"),
	APPLICATION_ByIDOFFER("/applicationByOfferid"),
	APPLICATION_BYMONTH("/applicationByMonth"),
	PREDICTION("/predict"),
	CalculWITH_ALLFILES("/scoreWithAllFiles"),
	
	/* URLApplicant*/
	FILE_CV("/fileContentJSON"),
	FILEID_BYUSER("/fileByUserId"),
	;

    private final String route;

    RecruteurRoute(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

}


