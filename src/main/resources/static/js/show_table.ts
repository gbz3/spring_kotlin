/*
  Settings > Languages & Frameworks > TypeScript > Options:
  https://www.typescriptlang.org/docs/handbook/compiler-options.html
 */
class Address {
    code: string
    oldZip: string
    zip: string
    kanaKen: string
    kanaShi: string
    kanaCho: string
    kanjiKen: string
    kanjiShi: string
    kanjiCho: string
}

let data: Array<Address> = []

async function loadPostalCode() {
    const response = await fetch('http://localhost:8080/api/table/postal_code')
    return await response.json() as Array<Address>
}

export async function getTableBody(f1, f2 = (a, b) => Number(a.zip) - Number(b.zip)): Promise<string> {
    data = await loadPostalCode()
    const tbody = data.filter(f1)
        .sort(f2)
        .map(addr => `<tr><td>${addr.zip}</td><td>${addr.kanjiKen}</td><td>${addr.kanjiShi}</td><td>${addr.kanjiCho}</td></tr>`)
        .join('')
    return `<thead><th>zip</th><th>kanjiKen</th><th>kanjiShi</th><th>kanjiCho</th></thead><tbody>${tbody}</tbody>`
}
