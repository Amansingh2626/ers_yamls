console.log("build: 882, AppVersion: $AppVersion");
 window.config = {
  language: "en",
  protocol: "http",
  server: "10.10.2.248",
  port: "30080",
  isSidebarLabelUpperCase: true,
  httpTimeout: "1200000", // in millis
  timeoutMessage: "UI Timeout",
  captchaRefreshTime: "60000", // in millis
  isAllowedTokenRefresh: false,
  service: "",
  locale: "/locales/{{lng}}/{{ns}}.json",
  currency: "BDT",
  openCalendarWith: "date",
  customer: "GrameenPhone",
  helplineNumber: "0012 345 678",
  contactValues:["viaSms","viaEmail","customerSupport"],
  additionalContactInformation:true,
  defaultTemplate: "",
  defaultPerPageItems: 100,
  timeZone: "UTC",
  hiddenModules: [], //"reseller", "account", "group" To disable specific modules
  channel: "web",
  captchaRegex: "^(?=.*\\d)(?=.*[A-Z])[0-9A-Z]{6}$",
  captchaTextStriked: true,
  forgotPwdResellerMSISDN: true,
  extraColumnConfigurations: {resellers: [{externalCode:"show" }, {parentExternalCode:"show" },{geographyCode:"show" }]}, //Unitel specific: Show extra columns for datatable for=> {resellers: [{ status: "hide"}, {balanceStatus: "show"}, {inventoryStatus:"show" }]}
  isBase64EncodePassword: false,
  unAuthConfig:{
    unauthorizedMessage: "Unauthorized Access",
    logoutOnUnAuth: true
  },
  loginOptions: {
    loginWithPassword: true,
    loginWithPasswordOTP: false,
    loginWithOTP: false,
    loginWithPasswordCaptcha: true,
  },
  modules: [
    "reseller",
    "product",
    "group",
    "report",
    "account",
    "contract",
    "commission",
    "bulk",
    "settings",
    "transaction",
    "notification",
    "wallet",
    "customerCare"
  ],
  activity: {
    adminType: [],
    createActivityRTP: "DLR-0"
  },
  bulk: {
    schedulerTypes: [
      { key: "Scheduled At", value: "scheduledAt" },
      { key: "Immediate", value: "immediate" },
      { key: "Scheduled", value: "scheduled" },
      { key: "Approval Pending", value: "approval-pending" },
      { key: "Rejected", value: "rejected" }
    ],
    failOnError: true,
    importSubTypeOptions: {
      Inventory: { type: "inventory", hasQueryParam: true },
      Reseller: { type: "reseller", hasQueryParam: true },
      Reselleruser: { type: "resellerUser", hasQueryParam: true },
      Order: { type: "order", hasQueryParam: true },
      RateCard: { type: "rateCard", hasQueryParam: false },
      ReverseOrder: { type: "reverseorder", hasQueryParam: false },
      Transaction: { type: "transaction", hasQueryParam: true },
      campaignTargets: { type: "campaignTargets", hasQueryParam: true },
      Product: { type: "product", hasQueryParam: true },
      Contract: { type: "contract", hasQueryParam: true },
    },
    exportSample: true,
    bulkImportFileExtension: ["csv"],
  },
  campaign: {
    status: [
      { key: "Created", value: "1" },
      { key: "Active", value: "2" },
      { key: "Hold", value: "3" },
      { key: "Approved", value: "4" },
      { key: "Disapproved", value: "5" },
      { key: "Closed", value: "6" },
      { key: "Unpaid", value: "7" },
      { key: "Partially Paid", value: "8" },
      { key: "Approval Pending", value: "9" },
      { key: "Drafted", value: "10" },
    ],
    statusValue: ["DRAFTED", "CREATED", "APPROVED", "DISAPPROVED", "REGISTERED", "REGISTRATION_FAILED"],
  },
  commission:{
    walletType: "RESELLER",
    commissionStatus: ["ACTIVE"],
  },
  crm: {
    fieldName: 'dealerId'
  },
  customerCare: {
    languageOptions:[
    { key: "English", value: "en" },
    { key: "Bengali", value: "bn" },
    ],
  },
  dashboard: {
    resellerTypes: ['branch', 'pos', 'hq_mpesa'],
    regionDataVals: ['ALL', 'Coast', 'RIFT', 'Great Western', 'Nairobi East'],
    monthData: [{ key: 'ALL', value: 'ALL' }],
    optionSelectLimit: 6,
    asA: ["Seller", "DROP_LOCATION"],
    agentType: ["DSA", "TDR", "3PL"],
    resellertypesRegional: ["hq", "branch", "hq_mpesa", "pos"],
  },
  group: {
    domainMaximumMembers: 10,
    showAdminTableInView: false,
    hideKeyOnGroupUpdate: [],
    showMsisdn:true
  },
  inventory: {
    colors: ['#FFF44C', '#E91E63', '#9C27B0', '#7CDDDD', '#FF7300', '#003F5C'],
    optionSelectLimit: 6, // Max should be 8 else donut chart UI will not look good
    callV2BoxesListingAPI: true, // If set to false then v1 api will get called
    fetchChildStockForBoxes: true,
  },
  livemap: {
    apiKey: 'AIzaSyC50SsFI2dFtN6Bygpt2YG4yOFgYQkp7X4',
    agentTypes: ['Agent', 'agent'],
    center: { lat: 22.5565568, lng: 87.895755 },
    zoom: 10,
  },
  order: {
    raiseOrderApplicationJson: true, // if OMS build no. is 354 or above then keep it true, else false
    mPesaCountdownTimer: 30, // in seconds and must be greater than 2
    employeeSnicCategory: "Employee",
    settleInvoiceDealerType: "hq",
    pisoOrderRouteInfoAdditionalField: "routeInfo",
    topup: {
      enabled: true,
      orderTypes: ["ISO", "ISO_ST", "IPO_D", "ISO_DST"],
    },
    serviceInfo: {
      enabled: true,
      serialisedTypes: ["PISO", "SO_ST", "ISO_ST", "ISO", "IPO", "IPO_ST", "ISRO_ST", "SO", "PO", "IPRO_ST"],
      nonSerialisedTypes: ["PISO", "SO_ST", "ISO_ST", "ISO", "IPO", "IPO_ST", "ISRO_ST", "SO", "PO", "IPRO_ST"],
      trackableNonSerialisedTypes: ["PISO", "SO_ST", "ISO_ST", "ISO", "IPO", "IPO_ST", "ISRO_ST", "SO", "PO", "IPRO_ST"],
      digitalTypes: ["ISO_D", "ISO_DST", "IPO_D"],
    },
    addtionalInformation: {
      enabled: true,
      fields: [
        {
          orderType: ["ISO_DST"],
          key: "senderPin",
          value: "",
          required: true,
          kind: "text",
          type: "password",
        },
        {
          orderType: ["PO", "IPO_D"],
          key: "POReference",
          value: "",
          required: true,
          kind: "text",
          type: "text",
        },
        {
          orderType: ["PO"],
          key: "LPO",
          value: "",
          required: true,
          kind: "file",
          type: "file",
        },
      ],
    },
    subscriberTypes: [],
    manualAdjustmentOptions: ["RESELLERID", "RESELLERMSISDN"],
    reversalOrderTypes: ["ISO", "ISO_DST", "ISO_D", "PISO"],
  },
  notification:{
    classifierTag:"CAMPAIGN_CLASSIFIER",
    recipientTag:"Receiver",
    transactionCategory:"CAMPAIGN",
    isUnified:true,
  },
  product: {
    policyTypes: ["Operation", "State", "State Transition", "Workflow"],
    showUnits: true,
    productTypes: ["serialised", "non-serialised", "services", "trackable-non-serialised", "services-b2b", "services-topup", "services-custom"]	  
  },
  region:{
    hideRegionSpecificCreateFields: false
  },
  report: {
    canScheduleReport: true,  
    ReportPerPageItems: 150,
    isDownloadReport: true,
    reportDownloadOptions: {
      csv: true,
      pdf: false,
      xls: false,
    },
    defaultRecordsPerPage: '100',
    recordsPerPage: [
     { key: "100", value: "100" },
     { key: "200", value: "200" },
     { key: "500", value: "500" },
     { key: "1000", value: "1000" },
     { key: "2000", value: "2000" },
     { key: "5000", value: "5000" }
    ],
    exportPdfFileDimension: [24, 50], // width, height in inches for report pdf download
    resellerTypesForDefaultDomain: ["DIST", "EADIST", "ECDIST", "ESDIST", "SDIST", "EUDIST"] // By default select domain name for this reseller types
  },
  resellerAdditionalConfig: {
    extensiveResellerDropdown: false,
    hideActionsWhenDeactivated: true,
    resellerSearchDomainBasedDropdowns: true,
    isDocumentRequired: false,
    onlyAllowedTypesForSearch: true,
    hideDataForResellerDetail: ["resellerType", "status", "externalCode"],
    isExternalCodeSearch: true,
    callIsBasicResellerInfoAPI: false,
    BasicResellerInfoAPILimit: 700,
    dropdownLimit: 50,
    dropdownOffset: 50,
    notificationMode:"",
    payLimitPopupinputDisabled: true,
    extensiveResellerDropdown: false,
    payLimitPopupinputRequired :false
  },
  settings: {
    roleForPolicyMapping: true,
    gatewayIpRegex: "^[0-9,.]*$",
    passwordMaxLength: "50",
    hideDateInPolicy: true,
  },
  transaction: {
    status: [
      { key: "Success", value: "success" },
      { key: "Pending", value: "pending" },
      { key: "Failed", value: "failed" },
      { key: "Denied", value: "denied" }
    ],
    transactionTypes: [
      {key:"AUTO CREDIT TRANSFER", value:"AUTO_CREDIT_TRANSFER"},
      {key:"O2C WITHDRAW", value:"O2C_WITHDRAW"},
      {key:"O2C SALE", value:"SALE"},
      {key:"O2C FOC", value:"FOC"},
      {key:"C2C TRANSFER", value:"TRANSFER"},
      {key:"C2C WITHDRAW", value:"C2C_WITHDRAW"},
      {key:"C2C RETURN", value:"RETURN"},
      {key:"Customer PROMORC", value:"C2S_PROMORC"},
      {key:"Skitto Recharge", value:"C2S_XRC"},
      {key:"Customer Recharge", value:"C2S_RC"},
      {key:"Promo Postpaid Recharge", value:"C2S_PROMOPPB"},
      {key:"Postpaid Bill Payment", value:"C2S_PPB"},
      {key:"Customer PVAS", value:"C2S_PVAS"},
      {key: 'Reversed Customer PVAS', value: 'REVERSE_C2S_PVAS' },
      {key:"P2P TRANSFER", value:"P2P"}
    ],
    customerCareTransactionTypes: [
      { key: 'Customer PROMORC', value: 'C2S_PROMORC' },
      { key: 'Skitto Recharge', value: 'C2S_XRC' },
      { key: 'Customer Recharge', value: 'C2S_RC' },
      { key: 'Promo Postpaid Recharge', value: 'C2S_PROMOPPB' },
      { key: 'Postpaid Bill Payment', value: 'C2S_PPB' },
      { key: 'P2P TRANSFER', value: 'P2P' },
      { key: 'Customer PVAS', value: 'C2S_PVAS' },
      { key: 'Reversed Customer PVAS', value: 'REVERSE_C2S_PVAS' },
      { key: 'O2C SALE', value:'SALE'},
      { key: 'O2C FOC', value:'FOC'},
      { key: 'O2C WITHDRAW', value:'O2C_WITHDRAW'},
      { key: 'C2C TRANSFER', value:'TRANSFER'},
      { key: 'C2C WITHDRAW', value:'C2C_WITHDRAW'},
      { key: 'C2C RETURN', value:'RETURN'}
    ],
    o2cSalePopupEligibleTags: ["COMMISSION"],
    o2cSaleProductSku: "O2C_transfer",
    o2cFOCValidationProductSku: "O2C_FOC_TRANSFER",
    c2cSaleProductSku: "C2C_transfer",
    c2cFOCValidationProductSku: "C2C_FOC_TRANSFER",
    focO2CApplicableContract: "1",
    saleO2CApplicableContract: "1",
    o2cFocProductSku: "O2C_FOC_TRANSFER",
    o2cWithdrawalProductSku: "O2C_WITHDRAW",
    o2cPendingTransaction: "O2C_transfer,O2C_FOC_TRANSFER",
    c2cSalePopupEligibleTags: ["COMMISSION"],
    c2cPendingTransaction: "C2C_TRANSFER",
    c2cRequestProductSku: "C2C_TRANSFER",
    c2cWithdrawalRequestProductSku: "C2C_WITHDRAW_TRANSFER",
    paymentOptions: [{ key: "CASH", value: "CASH" },],
    externalRequestType: [
      { key: "EXRCTRFREQ", value: "RC" },
      { key: "EXPROMORCTRFREQ", value: "PROMORC" },
      { key: "EXRCSTATREQ", value: "RC" },
      { key: "EXTPROMOPPBREQ", value: "PROMOPPB" },
      { key: "EXTPROMOVASTRFREQ", value: "PROMOVAS" },
      { key: "EXESBTRFREQ", value: "ESB" },
      { key: "EXPPBTRFREQ", value: "PPB" },
      { key: "EXXRCTRFREQ", value: "XRC" },
      { key: "EXTPROMOVASTRFREQ", value: "PVAS" },
    ],
    accountTypeIdMapping: [
      { key: "RC", value: "AIRTIME" },
      { key: "PROMORC", value: "AIRTIME" },
      { key: "PROMOPPB", value: "AIRTIME" },
      { key: "PROMOVAS", value: "AIRTIME" },
      { key: "ESB", value: "AIRTIME" },
      { key: 'PPB', value: "AIRTIME" },
      { key: "XRC", value: "SKITTO" },
      { key: "PVAS", value: "AIRTIME" }
    ],
    returnProductSku: "C2C_RETURN_TRANSFER",
    o2c: "OT",
    o2cWithdrawal: "OW",
    c2c: "CT",
    c2cWithdrawal: "CW",
    o2cPopUpTAXTagName:"TAX",
    o2cPopUpTAX2TagName:"TAX 2",
    o2cPopUpCOMISSIONTagName:"COMMISSION",
    o2cPopUpTDSTagName:"TDS",
    c2cPopUpTAXTagName:"TAX",
    c2cPopUpTAX2TagName:"TAX 2",
    c2cPopUpCOMISSIONTagName:"COMMISSION",
    c2cPopUpTDSTagName:"TDS",
    c2cReturn: "CR",
    c2sSale: "BD",
    c2cApplicableContract: "",
    REQUEST_GATEWAY_CODE: "WEB",
    hideTransactionInfo: [],
    hideTransactionInfoFromList: [],
    approvalTimeout: "30000", // in millis,
    instrumentType: "Cash",
    uploadFileLimit: 100
  },
  contract: {
    isRegionCreateContract: true,
    isAccountTypeFixedAsDropdown: true,
  },
  voucher:{
    voucherState: "ACTIVE"
  },
  wallet: {
    currencySymbol: " ",
  },
};

console.log("loaded config \n", window.config);
