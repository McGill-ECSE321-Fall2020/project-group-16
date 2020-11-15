<template>
  <div id="galleryEvents">
    <h2>Events</h2>
    <div v-if="errorEvent" class="alert alert-danger alert-dismissible fade show mx-auto" style="width: 100%" role="alert">
 {{errorEvent}}
  <button type="button" class="close" data-dismiss="alert" aria-label="Close" style="height:100%">
    <span aria-hidden="true">&times;</span>
  </button>
</div>

    <h3>Create new Event</h3>
    <table>
      <tr>
        <td>
          <input type="text" v-model="newEvent.name" placeholder="Event Name" />
        </td>
        <td>
          <input
            type="text"
            v-model="newEvent.description"
            placeholder="Event Description"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="newEvent.imageUrl"
            placeholder="Event Image Link"
          />
        </td>
        <td>
          <input type="date" v-model="newEvent.date" placeholder="YYYY-MM-DD" />
        </td>
        <td>
          <input type="time" v-model="newEvent.startTime" placeholder="HH:mm" />
        </td>
        <td>
          <input type="time" v-model="newEvent.endTime" placeholder="HH:mm" />
        </td>
        <td>
          <button
            v-bind:disabled="!newEvent.name"
            @click="
              createEvent(
                newEvent.name,
                newEvent.description,
                newEvent.imageUrl,
                newEvent.date,
                newEvent.startTime,
                newEvent.endTime
              )
            "
          >
            Create
          </button>
        </td>
      </tr>
    </table>
    <p>
      <div v-if="errorRequest" class="alert alert-danger alert-dismissible fade show mx-auto" style="width: 100%" role="alert">
 {{errorRequest}}
  <button type="button" class="close" data-dismiss="alert" aria-label="Close" style="height:100%">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
    </p>

    <h1>Current events</h1>
    <h2>Click one to register</h2>
    <div v-if="events.length !== 0" class="events my-5">
      <div class="row">
        <div class="col"><h3>Name</h3></div>
        <div class="col"><h3>Description</h3></div>
        <div class="col"><h3>Date</h3></div>
        <div class="col"><h3>Start Time</h3></div>
        <div class="col"><h3>End Time</h3></div>
      </div>
      <div
        class="row my-3 py-3 event"
        v-for="event in events"
        v-bind:key="event.id"
        @click="$router.push('events/' + event.id)"
      >
        <div class="col">{{ event.name }}</div>
        <div class="col">{{ event.eventDescription }}</div>
        <div class="col">{{ event.eventDate }}</div>
        <div class="col">{{ event.startTime }}</div>
        <div class="col">{{ event.endTime }}</div>
      </div>
    </div>
    <!-- <tr>
        <td>Name</td>
        <td>Event description</td>
        <td>Event date</td>
        <td>Event start time</td>
        <td>Event end time</td>
        <td>View event page</td>
      </tr>
      <tr v-for="event in events" v-bind:key="event.name">
        <td>{{event.name}}</td>
        <td>{{event.eventDescription}}</td>
        <td>{{event.eventDate}}</td>
        <td>{{event.startTime}}</td>
        <td>{{event.endTime}}</td>
        <td>
          <button @click="$router.push('/events/' + event.id)">here</button>
        </td>
      </tr> -->
    <p v-else>No events created, yet</p>
  </div>
</template>

<script src="../js/galleryEvent.js"></script>

<style scoped src="../css/galleryEvent.css">
table,
th,
td {
  border: 1px solid black;
}

.close:hover {
  background: transparent;
}
</style>
