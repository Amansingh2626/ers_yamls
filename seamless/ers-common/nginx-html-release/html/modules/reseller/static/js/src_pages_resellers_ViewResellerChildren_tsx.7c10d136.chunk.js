(self.webpackChunk_unified_reseller=self.webpackChunk_unified_reseller||[]).push([["src_pages_resellers_ViewResellerChildren_tsx"],{19767:(e,l,t)=>{"use strict";t.r(l),t.d(l,{default:()=>W});var n,r,a,s,o,i=t(51119),c=t(74315),u=t(2411),d=t(4179),m=t(33553),f=t(38670),p=t(79692),v=t(12420),g=t(70773),h=t(29609),E=t(54462),y=t(96545),S=t(23664),C=t(70331),R=t(6487),b=t(19869),I=t(84939),D=t(50969),T=t(6277),P=t(77790),Z=t.n(P),N=t(9692),F=t(91955),k=t(48049),O=t(67801),w=t(44104),A=t.n(w),B=t(15373);function x(e,l){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);l&&(n=n.filter((function(l){return Object.getOwnPropertyDescriptor(e,l).enumerable}))),t.push.apply(t,n)}return t}function j(e){for(var l=1;l<arguments.length;l++){var t=null!=arguments[l]?arguments[l]:{};l%2?x(Object(t),!0).forEach((function(l){(0,i.Z)(e,l,t[l])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):x(Object(t)).forEach((function(l){Object.defineProperty(e,l,Object.getOwnPropertyDescriptor(t,l))}))}return e}var L="",M=null,q=0,U=(null===(n=window.config)||void 0===n?void 0:n.defaultPerPageItems)||20,V=U,z=V,_=null,G=["resellerId","resellerMSISDN","resellerName","countryCode","status","contractId","resellerTypeName","parentResellerName"],J=(null===(r=document.querySelector(".MuiDataGridPanelContent-root"))||void 0===r||r.querySelector(".MuiIconButton-sizeSmall"),null===(a=window)||void 0===a||null===(s=a.config)||void 0===s?void 0:s.resellerAdditionalConfig),K=null!==(o=null==J?void 0:J.resetAdditionalFilters)&&void 0!==o&&o;const W=function(e){var l,t=e.customerCare,n=void 0!==t&&t,r=e.customFeatureList,a=void 0===r?[]:r,s=(0,h.useTranslation)().t,o=(0,S.Z)(),P=o.showErrorMessage,w=o.showSuccessMessage,x=(0,p.Z)((function(e){return(0,v.Z)({btnElement:{marginLeft:e.spacing(1),marginRight:e.spacing(1),whiteSpace:"nowrap",paddingLeft:e.spacing(2.5),paddingRight:e.spacing(2.5)},spaceElem:{margin:e.spacing(3)},element:{marginTop:e.spacing(2),marginBottom:e.spacing(2)},actionButtonSpacing:{marginBottom:e.spacing(2)},flexSpacingJustify:{justifyContent:"space-between"},uploadInput:{"& #seamless-unified-upload":{border:"2px solid ".concat(e.palette.success.main),borderRadius:"8px"}}})})),J=(0,k.useHistory)(),W=(0,f.useSelector)((function(e){return e.auth})).user,H=u.useState([]),Y=(0,c.Z)(H,2),Q=Y[0],X=Y[1],$=x(),ee=W.requestedPrincipalId,le=u.useState(!1),te=(0,c.Z)(le,2),ne=te[0],re=te[1],ae=u.useState(!1),se=(0,c.Z)(ae,2),oe=se[0],ie=se[1],ce=u.useState({limit:z,offset:q,perPage:V}),ue=(0,c.Z)(ce,2),de=ue[0],me=ue[1],fe=u.useState({reason:"",reasonDocument:"",resellerReasons:{},resellerReasonDocuments:{}}),pe=(0,c.Z)(fe,2),ve=pe[0],ge=pe[1],he=u.useState(null),Ee=(0,c.Z)(he,2),ye=Ee[0],Se=Ee[1],Ce=u.useState(!1),Re=(0,c.Z)(Ce,2),be=Re[0],Ie=Re[1],De=u.useState("1"),Te=(0,c.Z)(De,2),Pe=Te[0],Ze=Te[1],Ne=(0,B.Z)(),Fe=Ne.lifelineStates,ke=Ne.lifelineStatusFlag;function Oe(){_=null,z=U,q=0,V=U}var we=u.useCallback((function(e){re(!0),L=e;var l={},t={};for(var n in Q){var r=Q[n];l["".concat(r.resellerId)]="",t["".concat(r.resellerId)]=""}ge(j(j({},ve),{resellerReasons:l,resellerReasonDocuments:t}))}),[Q]),Ae=u.useCallback((function(){Ie(!0);var e=W.requestedPrincipalId?"RESELLERID":"RESELLERMSISDN",l={principalId:{id:W.requestedPrincipalId?W.requestedPrincipalId:W.targetMSISDN,type:e},operationType:L,channel:"PORTAL",clientId:W.userId,prepareOnly:!1,prepareRequired:!1,referredChainErsReference:"",pendingTimeout:6e5,customParameters:{globalReason:ve.reason},password:ve.password,targetPrincipalType:e,targetResellers:ve.resellerReasons};if("DEACTIVATE"===L){if("1"===Pe?Object.keys(ve.resellerReasonDocuments).forEach((function(e){l.customParameters["".concat(e)]=ve.reasonDocument})):Object.keys(ve.resellerReasonDocuments).forEach((function(e){l.customParameters["".concat(e)]=ve.resellerReasonDocuments[e]})),!ve.reason&&0===Object.keys(ve.resellerReasons).length||!ve.reasonDocument&&0===Object.keys(ve.resellerReasonDocuments).length)return P(s("missingFieldsErrorGeneral"))}else if(!ve.reason||0===Object.keys(ve.resellerReasons).length)return P(s("missingFieldsErrorGeneral"));(0,y.wv)(l).then((function(e){if(Ie(!1),0===e.resultCode){var l=e.resultDescription||e.resultMessage||"Status changed successfully";w("".concat(l)),re(!1),J.push("/home/reseller/resellers/children")}else if(e.batchId)Object.keys(e.resultDetails).forEach((function(l,t){var n=Q.find((function(e){return e.resellerId===l}));P("".concat(null==n?void 0:n.resellerName," - ").concat(e.resultDetails[l]))}));else{var t=e.resultDescription||"Sorry, Unable to change Resellers status: ".concat(L);P(t)}})).catch((function(e){return P(e)})).finally((function(){return Ie(!1)}))}),[Q,ve,W]),Be=(null===(l=window.config.resellerAdditionalConfig)||void 0===l?void 0:l.isExternalCodeSearch)||!1,xe=u.useCallback((function(e){var l,t,n=arguments.length>1&&void 0!==arguments[1]?arguments[1]:null,r=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{},a=arguments.length>3&&void 0!==arguments[3]?arguments[3]:function(){};z=(null==r?void 0:r.limit)||de.limit,V=(null==r?void 0:r.limit)||de.perPage;var o=null!==(l=null!==(t=null==r?void 0:r.page)&&void 0!==t?t:de.page)&&void 0!==l?l:0;null!=r&&r.offset&&(q=r.offset),e&&!n&&(q=0,o=0);var i=[],c=(null==e?void 0:e.additionalFields)||[];null!=e&&e.additionalFields&&(e.additionalFields.length>0&&(i=e.additionalFields),e.resellerTypeId&&delete e.additionalFields)," "===(null==e?void 0:e.contractId)&&(e.contractId=""),Be&&null!=e&&e.externalCode&&i.push({name:"externalCode",value:null==e?void 0:e.externalCode});var u={};if(ke){var d=n&&(null==n?void 0:n.lifecycleAttributes)||{},m=j(j({},d),{},{balanceStatus:null==e?void 0:e.balanceStatus,inventoryStatus:null==e?void 0:e.inventoryStatus});u.lifecycleAttributes=m}var f=j({},e);if(f.isAutotransfer?f.isAutotransfer=1:delete f.isAutotransfer,f.regionName&&""!==f.regionName.trim()&&(i.push({name:"regionName",value:f.regionName}),delete f.regionName),f.domainType&&""!==f.domainType){if(f.resellerTypeId&&""!==f.resellerTypeId){var p=null==f?void 0:f.resellerTypeId.concat(","+(null==f?void 0:f.domainType));f.resellerTypeId=p}else f.resellerTypeId=f.domainType;delete f.domainType}var v=j({additionalFields:i,resellerData:f},u);_=n||v;var g=W.requestedPrincipalId;if(ke)try{var h,E;delete v.status,null==v||null===(h=v.resellerData)||void 0===h||delete h.balanceStatus,null==v||null===(E=v.resellerData)||void 0===E||delete E.inventoryStatus}catch(e){}var S={additionalFields:[],resellerData:{resellerTypeId:"",contractId:"",resellerId:"",countryCode:"",resellerMSISDN:"",resellerName:"",additionalFields:[],domainType:"",regionName:" "}};Ie(!0),JSON.stringify(_)===JSON.stringify(S)?(0,y.OV)(g,q,z).then((function(e){var l;0===e.resultCode?(me(j(j({},de),{},{totalCount:e.totalCount,limit:z,offset:q,perPage:V,page:o})),Se((0,F.dh)(e.resellers)),null===(l=e.resellers)||void 0===l||l.length,ie(!0)):(P(e.resultDescription||s("resellerError")),Se(null))})).catch((function(e){return P(s("resellerError"))})).finally((function(){return Ie(!1)})):(0,y.FF)(_,z,q).then((function(e){if(0===e.resultCode){var l;me(j(j({},de),{},{totalCount:e.totalCount,limit:z,offset:q,perPage:V,page:o})),Se((0,F.dh)(e.resellers)),null===(l=e.resellers)||void 0===l||l.length,ie(!0)}else{var t=e.resultMessage||s("noDataFound","Sorry, No data found");P(t),Se(null)}})).catch((function(e){return P(e)})).finally((function(){Ie(!1),K?(a({target:{value:"",name:"resellerTypeId"}}),a({target:{value:"",name:"contractId"}}),a({target:{value:[],name:"additionalFields"}})):a({target:{value:c,name:"additionalFields"}})}))}),[Q,de,ve,ke]);u.useEffect((function(){return Oe(),n||ie(!0),function(){Oe()}}),[]);var je=u.useCallback((function(e){var l,t;null===(t=null===(l=document.querySelector(".MuiDataGridPanelContent-root"))||void 0===l?void 0:l.querySelector(".MuiIconButton-sizeSmall"))||void 0===t||t.addEventListener("click",(function(){var e;if(null!==(e=M)&&void 0!==e&&e.value)return J.push("/home/reseller/resellers/children")})),e.value&&(_=(0,i.Z)({},e.columnField,e.value),Ie(!0),q=0,M=e,G.includes(e.columnField)?xe(null,{resellerData:(0,i.Z)({},e.columnField,e.value)}):xe(null,{additionalFields:[{name:e.columnField,value:e.value}]}))}),[]),Le=u.useCallback((function(e,l){if(!e.resellerId||!e.newResellerParentId)return P(s("missingFieldsErrorGeneral"));(0,y.c1)(e).then((function(e){if(Ie(!1),0===e.resultCode)w("".concat(e.resultDescription)),J.push("/home/reseller/resellers/children"),l&&l(null,e);else{var t=e.resultDescription||"Sorry, Unable to change Parent Resellers";P(t),l&&l(e)}})).catch((function(e){return P(e)})).finally((function(){return Ie(!1)}))}),[]),Me=u.useCallback((function(e,l){e&&(Ie(!0),(0,y.E5)(e).then((function(e){0===e.resultCode?l&&l(null,e):l&&l(e)})).catch((function(e){return P(e)})).finally((function(){return Ie(!1)})))}),[]),qe=u.useCallback((function(e,l){Ie(!0);var t=Q.map((function(e){return e.resellerId}));(0,y.hm)(t,e.toLowerCase(),l.toLowerCase()).then((function(e){if(0===e.resultCode){var l=e.resultDescription||e.resultMessage||s("recordUpdateSuccess","Record updated successfully!");w("".concat(l)),J.push("/home/reseller/resellers/children")}else P((0,F.az)(e,s("recordUpdateError")))})).catch((function(e){return P((0,F.az)(e,s("recordUpdateError")))})).finally((function(){return Ie(!1)}))}),[Q]),Ue=A()(),Ve=Ue.checkPermission,ze=Ue.checkFederatedPermission;return u.createElement(g.SeamlessContainer,{title:s("viewAllResellerChildrenPageTitle"),description:s("viewAllResellerChildrenPageDescription")},u.createElement(m.Z,{display:"flex",flexDirection:"row",justifyContent:"flex-end",className:(0,T.default)($.actionButtonSpacing,$.flexSpacingJustify)},u.createElement("div",null,Q&&Q.length>0&&Ve(O.Z.EditReseller)&&u.createElement(m.Z,{display:"flex",flexDirection:"row",alignItems:"center"},ke?u.createElement(u.Fragment,null,Ve(O.Z.deactivateReseller)&&u.createElement(g.SeamlessButton,{onClick:function(){return qe("balance","activate")},color:"primary",variant:"outlined",className:$.btnElement},s("activateBalance")),Ve(O.Z.deactivateReseller)&&u.createElement(g.SeamlessButton,{onClick:function(){return qe("balance","terminate")},color:"primary",variant:"outlined",className:$.btnElement},s("terminateBalance")),Ve(O.Z.deactivateReseller)&&u.createElement(g.SeamlessButton,{onClick:function(){return qe("scratchcard","activate")},color:"primary",variant:"outlined",className:$.btnElement},s("activateInventory")),Ve(O.Z.deactivateReseller)&&u.createElement(g.SeamlessButton,{onClick:function(){return qe("scratchcard","terminate")},color:"primary",variant:"outlined",className:$.btnElement},s("terminateInventory"))):u.createElement(u.Fragment,null,(Ve(O.Z.blockReseller)||ze(a,O.Z.blockReseller))&&u.createElement(g.SeamlessButton,{onClick:function(){return we("BLOCK")},color:"primary",variant:"outlined",className:$.btnElement},s("BLOCKReseller")),Ve(O.Z.unblockReseller)&&u.createElement(g.SeamlessButton,{onClick:function(){return we("UNBLOCK")},color:"primary",variant:"outlined",className:$.btnElement},s("UNBLOCKReseller")),(Ve(O.Z.freezeReseller)||ze(a,O.Z.freezeReseller))&&u.createElement(g.SeamlessButton,{onClick:function(){return we("FREEZE")},color:"primary",variant:"outlined",className:$.btnElement},s("FREEZEReseller")),Ve(O.Z.unfreezeReseller)&&u.createElement(g.SeamlessButton,{onClick:function(){return we("UNFREEZE")},color:"primary",variant:"outlined",className:$.btnElement},s("UNFREEZEReseller")),(Ve(O.Z.deactivateReseller)||ze(a,O.Z.deactivateReseller))&&u.createElement(g.SeamlessButton,{onClick:function(){return we("DEACTIVATE")},color:"primary",variant:"outlined",className:$.btnElement},s("DEACTIVATEReseller")),Ve(O.Z.activateReseller)&&u.createElement(g.SeamlessButton,{onClick:function(){return we("ACTIVATE")},color:"primary",variant:"outlined",className:$.btnElement},s("ACTIVATEReseller"))))),u.createElement(d.WQ,null)),(Ve(O.Z.advancedSearchReseller)||ze(a,O.Z.advancedSearchReseller))&&u.createElement(d.xx,{data:{status:"ALL",resellerTypeId:"",contractId:"",resellerId:"",countryCode:"",resellerMSISDN:"",resellerName:"",isAutotransfer:!1,additionalFields:[],domainType:"",regionName:" "},metaData:{lifelineStatusFlag:ke,lifelineStates:Fe},loading:be,getTemplateHandler:Me,onFormSubmit:xe,customerCare:n}),oe&&u.createElement("div",{style:{height:600,width:"100%"}},u.createElement(d.yX,{id:ee,onPageChange:function(e){V=de.perPage,q=e*V,z=V,_?xe(null,_,{page:e}):me({offset:q,limit:z,perPage:V,page:e})},onPageSizeChange:function(e){q=0,z=V=e,_?xe(null,_,{limit:z,offset:q,page:0}):me({offset:q,limit:z,perPage:V,page:0})},searchResellers:ye,searchedData:_,isLoading:be,handleParentReseller:Le,paginationData:de,additionalProps:{lastFilter:M},handleFilterSearch:je,onSelectionModelChange:function(e,l){var t=e.selectionModel,n=l.filter((function(e){return t.includes(e.resellerId)}));return X(n)},customerCare:n})),u.createElement(E.default,{open:ne,onClose:function(){return re(!1)},onOk:Ae,dialogTitle:s("areYouSure"),dialogDescription:s("areYouSureOperation",{statusType:L})},u.createElement(b.ZP,{value:Pe},u.createElement(C.Z,{position:"static"},u.createElement(I.Z,{onChange:function(e,l){Ze(l)},"aria-label":"simple tabs example"},u.createElement(R.Z,{label:"General Reason",value:"1"}),u.createElement(R.Z,{label:"Individual Reason",value:"2"}))),u.createElement(D.Z,{value:"1"},u.createElement(g.SeamlessInput,{fullWidth:!0,required:!0,multiline:!0,rows:4,rowsMax:4,error:!ve.reason,className:$.element,value:ve.reason,onChange:function(e){return ge(j(j({},ve),{reason:e.target.value}))},label:s("enterGlobalReason",{reason:L}),helperText:s("reasonPlaceholder")}),"DEACTIVATE"===L&&u.createElement("div",{className:ve.reasonDocument?$.uploadInput:""},u.createElement(Z(),{name:"global-reason-doc",value:ve.reasonDocument,label:s("reasonDocumentPlaceholder"),onChange:function(e){if("image/png"===e.target.file.type){var l="".concat((0,N.getSrc)(e.target.value));ge(j(j({},ve),{reasonDocument:l}))}else P(s("pngFileSupport"))},resourceType:"agent"}),ve.reasonDocument&&s("uploadSuccessful"))),u.createElement(D.Z,{value:"2"},Q.map((function(e,l){return u.createElement("div",{key:l},u.createElement(g.SeamlessInput,{fullWidth:!0,className:$.element,name:"reason-".concat(l),value:ve.reason[e.resellerId],onChange:function(l){var t=ve.resellerReasons;t["".concat(e.resellerId)]=l.target.value,ge(j(j({},ve),{resellerReasons:t}))},label:s("enterReason",{reason:L,id:e.resellerId}),helperText:s("reasonPlaceholder")}),"DEACTIVATE"===L&&u.createElement("div",{className:ve.resellerReasonDocuments["".concat(e.resellerId)]?$.uploadInput:""},u.createElement(Z(),{name:"reason-doc-"+e.resellerId,value:ve.resellerReasonDocuments["".concat(e.resellerId)],label:s("reasonDocumentPlaceholder"),onChange:function(l){var t="".concat((0,N.getSrc)(l.target.value)),n=ve.resellerReasonDocuments;n["".concat(e.resellerId)]=t,ge(j(j({},ve),{resellerReasonDocuments:n}))},resourceType:"agent"}),ve.resellerReasonDocuments["".concat(e.resellerId)]&&s("uploadSuccessful")))}))))))}}}]);