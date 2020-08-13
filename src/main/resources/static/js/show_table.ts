/*
  Settings > Languages & Frameworks > TypeScript > Options:
  https://www.typescriptlang.org/docs/handbook/compiler-options.html
 */
// noinspection JSUnusedGlobalSymbols
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

let data: Address[] = []

async function loadData(): Promise<void> {
    if (data === null || data.length === 0) {
        const response = await fetch('http://localhost:8080/api/table/postal_code')
        data = await response.json() as Address[]
    }
}

// noinspection JSUnusedGlobalSymbols
export async function getTableBody(f1, f2 = (a, b) => Number(a.zip) - Number(b.zip)): Promise<string> {
    await loadData()
    const tbody = data.filter(f1)
        .sort(f2)
        .map(addr => `<tr><td>${addr.zip}</td><td>${addr.kanjiKen}</td><td>${addr.kanjiShi}</td><td>${addr.kanjiCho}</td></tr>`)
        .join('')
    return `<thead><th>zip</th><th>kanjiKen</th><th>kanjiShi</th><th>kanjiCho</th></thead><tbody>${tbody}</tbody>`
}

// noinspection JSUnusedGlobalSymbols
export async function getKenOptions(): Promise<string> {
    await loadData()
    const kens = Array.from(new Set(data.map(addr => `${addr.zip.substring(0, 2)}${addr.kanjiKen}`))).sort()
    return kens.map(k => `<option value="${k.substring(0, 2)}">${k.substring(2)} (${k.substring(0, 2)})</option>`).join()
}
