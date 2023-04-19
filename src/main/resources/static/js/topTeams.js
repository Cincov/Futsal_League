let loadStatsBtn = document.getElementById("topTeams");

loadStatsBtn.addEventListener('click', reloadStats)

function reloadStats() {

    let statsCntr = document.getElementById('teams-container')
    statsCntr.innerHTML = ''

    fetch("http://localhost:8080/api/teams/topThree").
    then(response => response.json()).
    then(json => json.forEach(team => {
        statsCntr.innerHTML += teamStatsAsHtml(team)
    }))
}

function teamStatsAsHtml(team) {
    let dogStatsHtml = '<tr>\n'
    dogStatsHtml += `<td >${team.name}</td>\n`
    dogStatsHtml += `<td >${team.rating}</td>\n`
    dogStatsHtml += `<td >${team.budget}</td>\n`
    dogStatsHtml += '</tr>\n'

    return dogStatsHtml
}