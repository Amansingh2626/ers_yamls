Location Based FlexiLoad Transaction Report for ${area} Area
ERS Sales ${data.date}

C2S : Total BDT ${data.totalC2sAmount} <% data.c2sThanaWise.eachWithIndex { v, i -> %>
${v.ownerName} - ${v.thanaName}  : BDT ${v.amount} <% } %>

C2C : Total BDT ${data.totalC2cAmount} <% data.c2cThanaWise.eachWithIndex { v, i -> %>
${v.ownerName} - ${v.thanaName}  : BDT ${v.amount} <% } %>

C2S (Channel to Subscriber) > STS & C2C (Channel to Channel) > STR. For any data mis-match, please revert back to Md. Shadlee Islam Ruhdan; 01711082115.
