(self.webpackChunk_unified_customercare=self.webpackChunk_unified_customercare||[]).push([[371],{99:(e,r,t)=>{var n=t(243);e.exports=function e(r,t,o){return n(t)||(o=t||o,t=[]),o=o||{},r instanceof RegExp?function(e,r){var t=e.source.match(/\((?!\?)/g);if(t)for(var n=0;n<t.length;n++)r.push({name:n,prefix:null,delimiter:null,optional:!1,repeat:!1,partial:!1,asterisk:!1,pattern:null});return l(e,r)}(r,t):n(r)?function(r,t,n){for(var o=[],i=0;i<r.length;i++)o.push(e(r[i],t,n).source);return l(new RegExp("(?:"+o.join("|")+")",f(n)),t)}(r,t,o):function(e,r,t){return s(i(e,t),r,t)}(r,t,o)},e.exports.parse=i,e.exports.compile=function(e,r){return p(i(e,r),r)},e.exports.tokensToFunction=p,e.exports.tokensToRegExp=s;var o=new RegExp(["(\\\\.)","([\\/.])?(?:(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?|(\\*))"].join("|"),"g");function i(e,r){for(var t,n=[],i=0,a=0,p="",l=r&&r.delimiter||"/";null!=(t=o.exec(e));){var f=t[0],s=t[1],g=t.index;if(p+=e.slice(a,g),a=g+f.length,s)p+=s[1];else{var h=e[a],x=t[2],d=t[3],v=t[4],m=t[5],w=t[6],y=t[7];p&&(n.push(p),p="");var E=null!=x&&null!=h&&h!==x,b="+"===w||"*"===w,k="?"===w||"*"===w,R=t[2]||l,$=v||m;n.push({name:d||i++,prefix:x||"",delimiter:R,optional:k,repeat:b,partial:E,asterisk:!!y,pattern:$?c($):y?".*":"[^"+u(R)+"]+?"})}}return a<e.length&&(p+=e.substr(a)),p&&n.push(p),n}function a(e){return encodeURI(e).replace(/[\/?#]/g,(function(e){return"%"+e.charCodeAt(0).toString(16).toUpperCase()}))}function p(e,r){for(var t=new Array(e.length),o=0;o<e.length;o++)"object"==typeof e[o]&&(t[o]=new RegExp("^(?:"+e[o].pattern+")$",f(r)));return function(r,o){for(var i="",p=r||{},u=(o||{}).pretty?a:encodeURIComponent,c=0;c<e.length;c++){var l=e[c];if("string"!=typeof l){var f,s=p[l.name];if(null==s){if(l.optional){l.partial&&(i+=l.prefix);continue}throw new TypeError('Expected "'+l.name+'" to be defined')}if(n(s)){if(!l.repeat)throw new TypeError('Expected "'+l.name+'" to not repeat, but received `'+JSON.stringify(s)+"`");if(0===s.length){if(l.optional)continue;throw new TypeError('Expected "'+l.name+'" to not be empty')}for(var g=0;g<s.length;g++){if(f=u(s[g]),!t[c].test(f))throw new TypeError('Expected all "'+l.name+'" to match "'+l.pattern+'", but received `'+JSON.stringify(f)+"`");i+=(0===g?l.prefix:l.delimiter)+f}}else{if(f=l.asterisk?encodeURI(s).replace(/[?#]/g,(function(e){return"%"+e.charCodeAt(0).toString(16).toUpperCase()})):u(s),!t[c].test(f))throw new TypeError('Expected "'+l.name+'" to match "'+l.pattern+'", but received "'+f+'"');i+=l.prefix+f}}else i+=l}return i}}function u(e){return e.replace(/([.+*?=^!:${}()[\]|\/\\])/g,"\\$1")}function c(e){return e.replace(/([=!:$\/()])/g,"\\$1")}function l(e,r){return e.keys=r,e}function f(e){return e&&e.sensitive?"":"i"}function s(e,r,t){n(r)||(t=r||t,r=[]);for(var o=(t=t||{}).strict,i=!1!==t.end,a="",p=0;p<e.length;p++){var c=e[p];if("string"==typeof c)a+=u(c);else{var s=u(c.prefix),g="(?:"+c.pattern+")";r.push(c),c.repeat&&(g+="(?:"+s+g+")*"),a+=g=c.optional?c.partial?s+"("+g+")?":"(?:"+s+"("+g+"))?":s+"("+g+")"}}var h=u(t.delimiter||"/"),x=a.slice(-h.length)===h;return o||(a=(x?a.slice(0,-h.length):a)+"(?:"+h+"(?=$))?"),a+=i?"$":o&&x?"":"(?="+h+"|$)",l(new RegExp("^"+a,f(t)),r)}},243:e=>{e.exports=Array.isArray||function(e){return"[object Array]"==Object.prototype.toString.call(e)}}}]);