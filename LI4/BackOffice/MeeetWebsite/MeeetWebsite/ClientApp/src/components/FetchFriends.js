import React, { Component } from 'react';

export class FetchFriends extends Component {
    static displayName = FetchFriends.name;

    constructor(props) {
        super(props);
        this.state = {
            friends: [],
            id: "",
            loading: true
        };
    }

    handleIdChange(event) {
        this.setState({ id: event.target.value })
    }

    async populateFriends(idInt) {
        const response = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getamizadesuser/' + idInt, { mode: 'cors' });
        const data = await response.json();
        console.log(data);

        const dataFriends = await fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUsersPerIDs/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        const friendsList = await dataFriends.json();
        this.setState({ friends: friendsList, loading: false });
    }

    static renderFriendsTable(friends) {
        return (
            <table className='table table-striped' aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    {friends.map(friend =>
                        <tr key={friend.id}>
                            <td>{friend.username}</td>
                            <td>{friend.email}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    render() {
        let contents = this.state.loading
            ? <p><em>Waiting...</em></p>
            : FetchFriends.renderFriendsTable(this.state.friends);

        return (
            <div>
                <h1 id="tabelLabel" >All Friends</h1>
                <p>This component demonstrates fetching data from the server.</p>
                {contents}
                <label>
                    Id de user:
                <input type="number" value={this.state.id} onChange={this.handleIdChange.bind(this)} />
                </label>
                <button onClick={(e) => { this.populateFriends(this.state.id); }}>See Friends</button>
            </div>
        );
    }
}
