(self.webpackChunk_unified_report=self.webpackChunk_unified_report||[]).push([[3596],{23493:(e,n,t)=>{for(var a=t(9057),c="undefined"==typeof window?t.g:window,l=["moz","webkit"],i="AnimationFrame",r=c["request"+i],o=c["cancel"+i]||c["cancelRequest"+i],u=0;!r&&u<l.length;u++)r=c[l[u]+"Request"+i],o=c[l[u]+"Cancel"+i]||c[l[u]+"CancelRequest"+i];if(!r||!o){var f=0,h=0,s=[];r=function(e){if(0===s.length){var n=a(),t=Math.max(0,16.666666666666668-(n-f));f=t+n,setTimeout((function(){var e=s.slice(0);s.length=0;for(var n=0;n<e.length;n++)if(!e[n].cancelled)try{e[n].callback(f)}catch(e){setTimeout((function(){throw e}),0)}}),Math.round(t))}return s.push({handle:++h,callback:e,cancelled:!1}),h},o=function(e){for(var n=0;n<s.length;n++)s[n].handle===e&&(s[n].cancelled=!0)}}e.exports=function(e){return r.call(c,e)},e.exports.cancel=function(){o.apply(c,arguments)},e.exports.polyfill=function(e){e||(e=c),e.requestAnimationFrame=r,e.cancelAnimationFrame=o}}}]);