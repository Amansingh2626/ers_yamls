(self.webpackChunk_unified_survey=self.webpackChunk_unified_survey||[]).push([[1296],{5072:(e,n,t)=>{"use strict";t.d(n,{k4:()=>S,CB:()=>O,Vb:()=>g,Z$:()=>b,uu:()=>w,Km:()=>q,Gn:()=>p,FE:()=>k,ht:()=>D,jX:()=>l,b:()=>E,dv:()=>d,R8:()=>R,HI:()=>I,AT:()=>P,oI:()=>L,cQ:()=>y,gp:()=>o,Av:()=>_,jY:()=>U,W4:()=>v,_8:()=>c,dk:()=>C,Ds:()=>s,z$:()=>i,if:()=>f,jN:()=>m,ru:()=>T,Tz:()=>h,xc:()=>j,cT:()=>A});var r=t(1416);const u=t.n(r)();var a=t(4126),o=function(e){var n="/surveymanagement/v1/survey"+"?".concat(a.stringify(e));return u(n)},c=function(e){var n="/surveymanagement/v1/survey/report"+"?".concat(a.stringify(e));return u(n)},v=function(e){var n="/surveymanagement/v1/survey/document/".concat(e,"/score-data");return u(n)},y=function(e){var n="/surveymanagement/v1/survey/document/".concat(e);return u(n)},s=function(){return u("/surveymanagement/v1/survey/reseller-attributes")},m=function(){return Promise.resolve({data:[{key:"Reseller ID",value:1},{key:"Reseller type",value:2},{key:"Group",value:3},{key:"All",value:4}]})},i=function(e){return u("/surveymanagement/v1/survey/".concat(e))},g=function(e){return u("/surveymanagement/v1/survey",{method:"POST",body:e})},d=function(){return u("/surveymanagement/v1/survey/applicable-types")},f=function(e){return u("/surveymanagement/v1/survey/".concat(e.surveyId))},l=function(e){return u("/surveymanagement/v1/survey/".concat(e.surveyId),{method:"PUT",body:e})},p=function(e){return u("/surveymanagement/v1/survey/".concat(e),{method:"DELETE"})},h=function(e){var n="/surveymanagement/v1/survey-type?".concat(a.stringify(e));return u(n)},b=function(e){return u("/surveymanagement/v1/survey-type",{method:"POST",body:e})},T=function(e){return u("/surveymanagement/v1/survey-type/".concat(e))},E=function(e,n){return u("/surveymanagement/v1/survey-type/".concat(e),{method:"PUT",body:n})},k=function(e){return u("/surveymanagement/v1/survey-type/".concat(e),{method:"DELETE"})},P=function(e){var n="/surveymanagement/v1/survey-category"+"?".concat(a.stringify(e));return u(n)},S=function(e){return u("/surveymanagement/v1/survey-category/".concat(null==e?void 0:e.categoryName),{method:"POST",body:e})},D=function(e){return u("/surveymanagement/v1/survey-category/".concat(e.id,"/").concat(null==e?void 0:e.categoryName),{method:"PUT",body:e})},O=function(e){var n=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"POST";return u("/surveymanagement/v1/category-question",{method:n,body:e})},q=function(e){return u("/surveymanagement/v1/category-question/".concat(e.id),{method:"DELETE"})},w=function(e){return u("/surveymanagement/v1/survey-category/".concat(e.id),{method:"DELETE"})},I=function(e){return u("/surveymanagement/v1/survey-category/".concat(e.id))},L=function(e){return u("/surveymanagement/v1/category-question/survey-category/".concat(e.id))},_=function(){return u("/surveymanagement/v1/question-type")},R=function(){return u("/surveymanagement/v1/autopick-question")},U=function(e){var n="/surveymanagement/v1/pending-survey"+"?".concat(a.stringify(e));return u(n)},j=function(e){return u("/surveymanagement/v1/survey/submitSurvey",{method:"POST",body:e})},A=function(e,n){var t="/osm/v1/resource/".concat(n);return u(t,{method:"POST",body:e},!0)},C=function(e,n){var t=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"v1",r=window.config,u="".concat(r.protocol,"://").concat(r.server,":").concat(r.port,"/api"),a="".concat(u,"/osm/").concat(t,"/resource/").concat(e,"/").concat(n);return new URL(a).toString()}}}]);