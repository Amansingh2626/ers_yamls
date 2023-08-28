(self.webpackChunk_unified_settings=self.webpackChunk_unified_settings||[]).push([[3710],{19258:(t,e,n)=>{"use strict";n.d(e,{Z:()=>ct});var r=n(53349),o=n.n(r),u=n(58163),i=n.n(u),p=n(13003),a=n.n(p),f=n(66402),s=n.n(f),c=n(49029),y=n.n(c),h=n(25602),l=n.n(h),M=n(27526),m=n.n(M),d=n(64958),g=n.n(d),D=n(55695),H=n.n(D),T=n(31349),x=n.n(T),v=n(2354),E=n.n(v),k=n(28410),F=n.n(k),A=n(80410),S=n.n(A),Y=n(85324),w=n.n(Y),N=n(74173),O=n.n(N),P=n(66322),_=n.n(P),b=n(35069),B=n.n(b),C=n(25468),W=n.n(C),q=n(76026),R=n.n(q),V=n(33187),Z=n.n(V),j=n(18838),z=n.n(j),G=n(54988),I=n.n(G),J=n(74553),K=n.n(J),L=n(25910),Q=n.n(L),U=n(6401),X=n.n(U),$=n(90508),tt=n.n($),et=n(92066),nt=n.n(et),rt=n(68867),ot=n.n(rt),ut=n(35073),it=n.n(ut),pt=n(59246),at=n.n(pt),ft=n(21081),st=n.n(ft);const ct=function(){function t(t){var e=(void 0===t?{}:t).locale;this.yearFormat="yyyy",this.yearMonthFormat="MMMM yyyy",this.dateTime12hFormat="MMMM do hh:mm aaaa",this.dateTime24hFormat="MMMM do HH:mm",this.time12hFormat="hh:mm a",this.time24hFormat="HH:mm",this.dateFormat="MMMM do",this.locale=e}return t.prototype.addDays=function(t,e){return o()(t,e)},t.prototype.isValid=function(t){return Z()(this.date(t))},t.prototype.getDiff=function(t,e){return s()(t,this.date(e))},t.prototype.isAfter=function(t,e){return S()(t,e)},t.prototype.isBefore=function(t,e){return w()(t,e)},t.prototype.startOfDay=function(t){return nt()(t)},t.prototype.endOfDay=function(t){return l()(t)},t.prototype.getHours=function(t){return x()(t)},t.prototype.setHours=function(t,e){return I()(t,e)},t.prototype.setMinutes=function(t,e){return K()(t,e)},t.prototype.getSeconds=function(t){return E()(t)},t.prototype.setSeconds=function(t,e){return X()(t,e)},t.prototype.isSameDay=function(t,e){return _()(t,e)},t.prototype.isSameMonth=function(t,e){return W()(t,e)},t.prototype.isSameYear=function(t,e){return B()(t,e)},t.prototype.isSameHour=function(t,e){return R()(t,e)},t.prototype.startOfMonth=function(t){return ot()(t)},t.prototype.endOfMonth=function(t){return it()(t)},t.prototype.getYear=function(t){return F()(t)},t.prototype.setYear=function(t,e){return tt()(t,e)},t.prototype.date=function(t){return void 0===t?new Date:null===t?null:new Date(t)},t.prototype.parse=function(t,e){return""===t?null:z()(t,e,new Date,{locale:this.locale})},t.prototype.format=function(t,e){return H()(t,e,{locale:this.locale})},t.prototype.isEqual=function(t,e){return null===t&&null===e||O()(t,e)},t.prototype.isNull=function(t){return null===t},t.prototype.isAfterDay=function(t,e){return S()(t,l()(e))},t.prototype.isBeforeDay=function(t,e){return w()(t,nt()(e))},t.prototype.isBeforeYear=function(t,e){return w()(t,st()(e))},t.prototype.isAfterYear=function(t,e){return S()(t,g()(e))},t.prototype.formatNumber=function(t){return t},t.prototype.getMinutes=function(t){return t.getMinutes()},t.prototype.getMonth=function(t){return t.getMonth()},t.prototype.setMonth=function(t,e){return Q()(t,e)},t.prototype.getMeridiemText=function(t){return"am"===t?"AM":"PM"},t.prototype.getNextMonth=function(t){return i()(t,1)},t.prototype.getPreviousMonth=function(t){return i()(t,-1)},t.prototype.getMonthArray=function(t){for(var e=[st()(t)];e.length<12;){var n=e[e.length-1];e.push(this.getNextMonth(n))}return e},t.prototype.mergeDateAndTime=function(t,e){return this.setMinutes(this.setHours(t,this.getHours(e)),this.getMinutes(e))},t.prototype.getWeekdays=function(){var t=this,e=new Date;return y()({start:at()(e,{locale:this.locale}),end:m()(e,{locale:this.locale})}).map((function(e){return t.format(e,"EEEEEE")}))},t.prototype.getWeekArray=function(t){for(var e=at()(ot()(t),{locale:this.locale}),n=m()(it()(t),{locale:this.locale}),r=0,u=e,i=[];w()(u,n);){var p=Math.floor(r/7);i[p]=i[p]||[],i[p].push(u),u=o()(u,1),r+=1}return i},t.prototype.getYearRange=function(t,e){for(var n=st()(t),r=g()(e),o=[],u=n;w()(u,r);)o.push(u),u=a()(u,1);return o},t.prototype.getCalendarHeaderText=function(t){return this.format(t,this.yearMonthFormat)},t.prototype.getYearText=function(t){return this.format(t,"yyyy")},t.prototype.getDatePickerHeaderText=function(t){return this.format(t,"EEE, MMM d")},t.prototype.getDateTimePickerHeaderText=function(t){return this.format(t,"MMM d")},t.prototype.getMonthText=function(t){return this.format(t,"MMMM")},t.prototype.getDayText=function(t){return this.format(t,"d")},t.prototype.getHourText=function(t,e){return this.format(t,e?"hh":"HH")},t.prototype.getMinuteText=function(t){return this.format(t,"mm")},t.prototype.getSecondText=function(t){return this.format(t,"ss")},t}()}}]);