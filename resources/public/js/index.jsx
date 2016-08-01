const Counter = React.createClass({
  render: function() {
    return (
        <div>
          <h4>Counter [id: {this.props.id}]</h4>
          Value: {this.props.counter}
        </div>
        );
  }
});

const CounterController = React.createClass({
  remove: function() {
    return triforce.store.dispatch("REMOVE", this.props.id);
  },

  inc: function() {
    return triforce.store.dispatch("INC", this.props.id);
  },

  dec: function() {
    return triforce.store.dispatch("DEC", this.props.id);
  },

  reset: function() {
    return triforce.store.dispatch("RESET", this.props.id);
  },

  add: function(n) {
    return triforce.store.dispatch("ADD", n, this.props.id);
  },

  render: function() {
    return (
        <div>
          <span className="btn btn-xs btn-danger" onClick={this.remove}>remove</span>
          <span className="btn btn-xs btn-primary" onClick={this.reset}>reset</span>
          <span className="btn btn-xs btn-primary" onClick={this.dec}>-</span>
          <span className="btn btn-xs btn-primary" onClick={this.inc}>+</span>
        </div>
        );
  }
});

const CounterAdd = React.createClass({
  newCounter: function() {
    return triforce.store.dispatch("NEW");
  },

  render: function() {
    return (
        <div>
          <span className="btn btn-xs btn-danger" onClick={this.newCounter}>Add counter</span>
        </div>
        );
  }

});

const CounterList = React.createClass({
  render: function() {
    return (
        <div className="jumbotron">
          <h3>Counter List Demo</h3>
          <CounterAdd />
          {this.props.counters.map( function(counter) {
              return (
                <div key={counter.id}>
                  <Counter counter={counter.value} id={counter.id} />
                  <CounterController id={counter.id} />
                </div>);
              }
            )
          }
        </div>
        );
  }
});

const App = React.createClass({
  render: function() {
    return (
        <div className="container">
          <CounterList counters={this.props.counters} />
        </div>
        );
  }
});

// Mounting the DOM
React.render(React.createElement(triforce.store.provider(App)), document.getElementById("app"));
