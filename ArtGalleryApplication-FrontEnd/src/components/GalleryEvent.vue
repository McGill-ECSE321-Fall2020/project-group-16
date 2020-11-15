<template>
  <div id="galleryEvents">
    <div v-if="createModalActive" class="modal-container">
      <div
        v-if="errorEvent !== ''"
        class="alert alert-danger alert-dismissible fade show mx-auto mb-4"
        role="alert"
      >
        <strong>{{ error }}</strong>
        <button
          type="button"
          class="close"
          data-dismiss="alert"
          aria-label="Close"
        >
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="edit-modal p-4 modal">
        <i class="fas fa-times fa-lg close" @click="hideModal()"></i><br />
        <h1 class="mb-3">Create Event</h1>
        <input
          id="name"
          v-model="newEvent.name"
          type="text"
          placeholder="Event Name"
        /><br />
        <textarea
          rows="5"
          cols="30"
          id="description"
          v-model="newEvent.description"
          type="text"
          placeholder="Event Description"
        />
        <br />
        <input
          id="date"
          v-model="newEvent.date"
          type="date"
          placeholder="YYYY-MM-DD"
        /><br />
        <input
          type="time"
          name=""
          id="start-time"
          placeholder="HH:MM"
          v-model="newEvent.startTime"
        />
        <br />
        <input
          type="time"
          name=""
          id="end-time"
          v-model="newEvent.endTime"
          placeholder="HH:MM"
        />
        <div
          class="save-button px-3 py-2 mx-auto mt-3"
          @click="
            createEvent(
              newEvent.name,
              newEvent.description,
              newEvent.date,
              newEvent.startTime,
              newEvent.endTime
            )
          "
        >
          SAVE
        </div>
      </div>
    </div>
    <div class="create-button mx-auto px-3 py-2" @click="showModal">
      CREATE EVENT
    </div>

    <div
      v-if="errorRequest"
      class="alert alert-danger alert-dismissible fade show mx-auto"
      style="width: 100%"
      role="alert"
    >
      {{ errorRequest }}
      <button
        type="button"
        class="close"
        data-dismiss="alert"
        aria-label="Close"
        style="height: 100%"
      >
        <span aria-hidden="true">&times;</span>
      </button>
    </div>

    <!-- <div
      v-if="errorRequest"
      class="alert alert-danger alert-dismissible fade show mx-auto"
      style="width: 100%"
      role="alert"
    >
      {{ errorRequest }}
      <button
        type="button"
        class="close"
        data-dismiss="alert"
        aria-label="Close"
        style="height: 100%"
      >
        <span aria-hidden="true">&times;</span>
      </button>
    </div> -->

    <h1 style="text-align: center">Current events</h1>
    <h2 style="text-align: center">Click one to register</h2>
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
    <h2 class="my-3" style="text-align: center" v-else>
      No events created yet
    </h2>
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
