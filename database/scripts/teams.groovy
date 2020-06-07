def conferences = [
        'BIG_TEN': [name: 'Big Ten'],
        'SEC': [name: 'SEC'],
        'PAC_12': [name: 'Pac 12'],
        'BIG_12': [name: 'Big 12']
]

def teams = [
//        Big Ten
[name: 'Iowa', nickname: 'Hawkeyes', conference: 'BIG_TEN'],
[name: 'Nebraska', nickname: 'Cornhuskers', conference: 'BIG_TEN'],
[name: 'Illinois', nickname: 'Illini', conference: 'BIG_TEN'],
[name: 'Indiana', nickname: 'Hoosiers', conference: 'BIG_TEN'],
[name: 'Maryland', nickname: 'Terrapins', conference: 'BIG_TEN'],
[name: 'Michigan', nickname: 'Wolverines', conference: 'BIG_TEN'],
[name: 'Minnesota', nickname: 'Golden Gophers', conference: 'BIG_TEN'],
[name: 'Michigan State', nickname: 'Spartans', conference: 'BIG_TEN'],
[name: 'Ohio State', nickname: 'Buckeyes', conference: 'BIG_TEN'],
[name: 'Penn State', nickname: 'Nittany Lions', conference: 'BIG_TEN'],
[name: 'Northwestern', nickname: 'Wildcats', conference: 'BIG_TEN'],
[name: 'Purdue', nickname: 'Boilermakers', conference: 'BIG_TEN'],
[name: 'Rutgers', nickname: 'Scarlet Knights', conference: 'BIG_TEN'],
[name: 'Wisconsin', nickname: 'Badgers', conference: 'BIG_TEN'],

//        SEC
[name: 'Alabama', nickname: 'Crimson Tide', conference: 'SEC'],
[name: 'Arkansas', nickname: 'Razorbacks', conference: 'SEC'],
[name: 'Auburn', nickname: 'Tigers', conference: 'SEC'],
[name: 'Florida', nickname: 'Gators', conference: 'SEC'],
[name: 'Georgia', nickname: 'Bulldogs', conference: 'SEC'],
[name: 'Kentucky', nickname: 'Wildcats', conference: 'SEC'],
[name: 'LSU', nickname: 'Tigers', conference: 'SEC'],
[name: 'Mississippi', nickname: 'Bulldogs', conference: 'SEC'],
[name: 'Missouri', nickname: 'Tigers', conference: 'SEC'],
[name: 'Ole Miss', nickname: 'Rebels', conference: 'SEC'],
[name: 'South Carolina', nickname: 'Gamecocks', conference: 'SEC'],
[name: 'Tennessee', nickname: 'Volunteers', conference: 'SEC'],
[name: 'Texas A&M', nickname: 'Aggies', conference: 'SEC'],
[name: 'Vanderbilt', nickname: 'Commodores', conference: 'SEC'],

//        Pac-12
[name: 'Arizona', nickname: 'Wildcats', conference: 'PAC_12'],
[name: 'Arizona State', nickname: 'Sun Devils', conference: 'PAC_12'],
[name: 'Cal', nickname: 'Golden Bears', conference: 'PAC_12'],
[name: 'Colorado', nickname: 'Buffaloes', conference: 'PAC_12'],
[name: 'Oregon', nickname: 'Ducks', conference: 'PAC_12'],
[name: 'Oregon State', nickname: 'Beavers', conference: 'PAC_12'],
[name: 'Stanford', nickname: 'Cardinal', conference: 'PAC_12'],
[name: 'UCLA', nickname: 'Bruins', conference: 'PAC_12'],
[name: 'USC', nickname: 'Trojans', conference: 'PAC_12'],
[name: 'Utah', nickname: 'Utes', conference: 'PAC_12'],
[name: 'Washington', nickname: 'Huskies', conference: 'PAC_12'],
[name: 'Washington State', nickname: 'Cougars', conference: 'PAC_12'],

//        Big 12
[name: 'Baylor', nickname: 'Bears', conference: 'BIG_12'],
[name: 'Iowa State', nickname: 'Cyclones', conference: 'BIG_12'],
[name: 'Kansas', nickname: 'Jayhawks', conference: 'BIG_12'],
[name: 'Kansas State', nickname: 'Wildcats', conference: 'BIG_12'],
[name: 'Oklahoma', nickname: 'Sooners', conference: 'BIG_12'],
[name: 'Oklahoma State', nickname: 'Cowboys', conference: 'BIG_12'],
[name: 'TCU', nickname: 'Horned Frogs', conference: 'BIG_12'],
[name: 'Texas', nickname: 'Longhorns', conference: 'BIG_12'],
[name: 'Texas Tech', nickname: 'Red Raiders', conference: 'BIG_12'],
[name: 'West Virginia', nickname: 'Mountaineers', conference: 'BIG_12']
]

conferences.keySet().each { it ->
    def conference = conferences[it]
    conference.id = UUID.randomUUID()

    println "INSERT INTO conference(id, name) VALUES ('${conference.id}', '${conference.name}');"

}

teams.each {team ->
    team.id = UUID.randomUUID()
    team.conferenceId = conferences[team.conference].id
    println "INSERT INTO team(id, name, nick_name, conference) VALUES('${team.id}', '${team.name}', '${team.nickname}', '${team.conferenceId}');"

}
