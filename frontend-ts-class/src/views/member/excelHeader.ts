export const excelHeader = [
    'Name',
    'Address',
    'Phone',
    'Email',
    'Fax',
    'Class Name',
    'Special Date Name 1',
    'Special Date 1',
    'Special Date Remark 1',
    'Special Date Name 2',
    'Special Date 2',
    'Special Date Remark 2'
]

export function formatSpecialDate(arr: any) {
    return arr.map(obj => {
        const memberSpecialDays: { name: string; date: string; remark?: string }[] = []
    
        // Loop through the keys in the object
        for (const key in obj) {
          // Look for keys that start with 'Special Date Name'
          if (key.startsWith("Special Date Name")) {
            // Extract the index number from the key
            const index = key.replace("Special Date Name ", "")
    
            // Find the corresponding 'Special Date' and 'Special Date Remark' (if available)
            const dateKey = `Special Date ${index}`;
            const remarkKey = `Special Date Remark ${index}`

            
            let timestamp = new Date(obj[dateKey])
            console.log(timestamp, 'test')
            // Construct the memberSpecialDays entry with optional remark
            const memberSpecialDay: { name: string; date: string; remark?: string } = {
              name: obj[key],
              date: obj[dateKey]
            };
    
            // Add remark if it exists
            if (obj[remarkKey]) {
              memberSpecialDay.remark = obj[remarkKey]
            }
    
            // Push the entry into the array
            memberSpecialDays.push(memberSpecialDay)
          }
        }
    
        // Mapping field names to camelCase
        const transformedObj = {
          name: obj["Name"],
          address: obj["Address"],
          phone: obj["Phone"],
          email: obj["Email"],
          fax: obj["Fax"],
          className: obj["Class Name"].trim(), // Handle any extra spaces
          memberSpecialDays
        };
    
        return transformedObj
      })
}