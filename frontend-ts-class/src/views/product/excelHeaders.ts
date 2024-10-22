export const excelHeader = [
    'Product Code',
    'Product Name',
    'Item Code',
    'Brand Name',
    'Type Code ',
    'Type Name',
    'Unit',
    'Description',
    'Retail Price',
    'Department Code',
    'Department Name',
    'Vendor Code',
    'Vendor Name',
    'Other Vendor Name',
    'Vendor Type',
    'Vendor Email',
    'Vendor Phone',
    'Vendor Fax',
    'Vendor Address',
    'Vendor Contact Person',
    'Vendor Remark',
    'Tax',
    'Tax Code',
    'Tax Rate(%)',
    'After Tax',
    'Tax Amount',
    'Location Code 1',
    'Location Name 1',
    'Location Qty 1',
 //   'Stock Move to Location Code 1',
 //   'Stock Move to Location Name 1',
 //   'Stock Move Qtys 1',
    'Location Code 2',
    'Location Name 2',
    'Location Qty 2',
//    'Stock Move to Location Code 2',
 //   'Stock Move to Location Name 2',
 //   'Stock Move Qtys 2',
]

export function formatData(arr: any) {

    


    return arr.map((obj: any) => {
        const retailPrice = Number(obj["Retail Price"]) || 0
        const taxRate = Number(obj['Tax Rate(%)']) / 100 || 0
        const afterTax = Number(obj['After Tax']) ? Number(obj['After Tax']) : retailPrice * taxRate
        const taxAmount = Number(obj['Tax Amount']) ? Number(obj['Tax Amount']) : afterTax - retailPrice


        const productLocations: { 
            placeCode: string; 
            placeName: string; 
            qty: number, 
            stockMovePlaceToCode?: string;
            stockMovePlaceToName?: string;
            stockMovePlaceToQty?: number;
        }[] = []
    
        // Loop through the keys in the object
        for (const key in obj) {
          // Look for keys that start with 'Special Date Name'
          if (key.startsWith("Location Code")) {
            // Extract the index number from the key
            const index = key.replace("Location Code ", "")
    
            // Find the corresponding 'Special Date' and 'Special Date Remark' (if available)
            const nameKey = `Location Name ${index}`;
            const qtyKey = `Location Qty ${index}`
            const qty = Number(obj[qtyKey])

            // Construct the memberSpecialDays entry with optional remark
            const memberSpecialDay: any = {
                placeCode: obj[key],
                placeName: obj[nameKey],
                qty,
                totalPrice: afterTax ? afterTax : retailPrice * qty
            };
            
    
            // Add if it exists
            if (obj[`Stock Move to Location Code ${index}`]) {
                memberSpecialDay.stockMovePlaceToCode = obj[`Stock Move to Location Code ${index}`]
            }

            if (obj[`Stock Move to Location Name ${index}`]) {
                memberSpecialDay.stockMovePlaceToName = obj[`Stock Move to Location Name ${index}`]
            }
            if (obj[`Stock Move Qtys ${index}`]) {
                memberSpecialDay.stockMovePlaceToQty = obj[`Stock Move Qtys ${index}`]
                memberSpecialDay.stockMovePlaceToTotalPrice = afterTax ? afterTax : retailPrice * obj[`Stock Move Qtys ${index}`]
            }
            
    
            // Push the entry into the array
            productLocations.push(memberSpecialDay)
          }
        }
    
        // Mapping field names to camelCase
        const transformedObj = {
            productCode: obj["Product Code"],
            productName: obj["Product Name"],
            itemCode: obj["Item Code"],
            brandName: obj["Brand Name"],
            typeCode: obj["Type Code"],
            typeName: obj["Type Name"],
            unit: obj["Unit"],
            description: obj["description"],
            retailPrice,
            departmentCode: obj["Department Code"],
            departmentName: obj["Department Name"],
            vendorCode: obj["Vendor Code"],
            vendorName: obj["Vendor Name"],
            vendorOtherName: obj['Other Vendor Name'],
            vendorType: obj['Vendor Type'],
            vendorEmail: obj['Vendor Email'],
            vendorPhone: obj['Vendor Phone'],
            vendorFax: obj['Vendor Fax'],
            vendorAddress: obj['Vendor Address'],
            vendorContactPerson: obj['Vendor Contact Person'],
            vendorRemark: obj['Vendor Remark'],
            tax: obj['Tax'] === 'Yes'? 1 : 0,
            taxCode: obj['Tax Code'],
            taxRate,
            afterTax,
            taxAmount,
            productLocations
        };
    
        return transformedObj
      })
}