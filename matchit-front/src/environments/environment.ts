// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  apiHost: 'http://localhost:8762/',

      /**user */
      authuser:'user/authenticate',
      reguser:'user/registrate',
      deleteuser:'user/Delete/',
      getuser:'user/',
      getallusers:'user/AllUsers',
      newpwdlink:'user/NewPassword',
      updateuser:'user/Update',
      getuserbyrole:'user/role/',
      /**offre */
      pagination: 'manager/pagination/',
      listOffer: 'manager/offerList',
      addOffer:'manager/saveOffer',
      updateOffer:'manager/updateOffer',
      getOfferbyId:'manager/getOfferById',
      deleteOffer:'manager/deleteOffer',
      getOfferNotExp : 'manager/getOfferNotEpired',
      getOfferNotExpired:'manager/getOfferNotEpiredPagination',
      getOfferByStatus: 'manager/paginationAndSort',
      /**applicattion */
      getallapplication:'calculator/allApplication',
      getapplicationPaginated : 'calculator/paginationApplication/',
      getApplicationOrderByMonth : 'calculator/applicationByMonth',
      getApplicationByuserId : 'calculator/applicationByUserId/',
      getApplicationByUserIdPaginated : 'calculator/applicationByUserIdPaginated/',
      getApplicationByidoffer: 'calculator/applicationByOfferid/',
      getApplicationByidOfferByuserId : 'calculator/applicationByOfferidAndUserId/',
      deleteApplication : 'calculator/deleteApplication',
      getCVCandidat : 'applicant/fileContentJSON',
      getFileByUserId : 'applicant/fileByUserId',
      updateCV : 'applicant/updateContent/',
      /**postuler */
      uploadCV: 'applicant/upload',
      calculScore: 'calculator/score',
      reCalculScore: 'calculator/reCalcul',
      postuler: 'calculator/updateApplication',
      scoreAllFiles : 'calculator/scoreWithAllFiles/',
      /**classification */
      prediction : 'calculator/predict'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
